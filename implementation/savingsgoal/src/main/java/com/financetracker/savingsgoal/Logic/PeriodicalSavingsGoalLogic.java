package com.financetracker.savingsgoal.Logic;

import com.financetracker.savingsgoal.Duration;
import com.financetracker.savingsgoal.PeriodicalSavingsGoal;
import com.financetracker.savingsgoal.Time.Configuration;
import com.financetracker.savingsgoal.client.TransactionClient;
import com.financetracker.savingsgoal.kafka.KafkaSendMessages;
import com.financetracker.savingsgoal.model.PeriodicalSavingsGoalMapper;
import com.financetracker.savingsgoal.model.PeriodicalSavingsGoalRepository;
import com.financetracker.savingsgoal.model.SavingsGoalMapper;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.Periodicity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PeriodicalSavingsGoalLogic {

    private final KafkaSendMessages kafkaSendMessages;
    private final TransactionClient transactionClient;
    private final PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;
    private final SavingsGoalMapper savingsGoalMapper;
    private final PeriodicalSavingsGoalMapper periodicalSavingsGoalMapper;

    public PeriodicalSavingsGoalLogic(KafkaSendMessages kafkaSendMessages, TransactionClient transactionClient, PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository, SavingsGoalMapper savingsGoalMapper, PeriodicalSavingsGoalMapper periodicalSavingsGoalMapper) {
        this.kafkaSendMessages = kafkaSendMessages;
        this.transactionClient = transactionClient;
        this.periodicalSavingsGoalRepository = periodicalSavingsGoalRepository;
        this.savingsGoalMapper = savingsGoalMapper;
        this.periodicalSavingsGoalMapper = periodicalSavingsGoalMapper;
    }

    public List<PeriodicalSavingsGoalDTO> getPeriodicalSavingsGoals() {
        List<PeriodicalSavingsGoalDTO> periodicalSavingsGoalDTOList = new ArrayList<>();
        for (PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()) {
            periodicalSavingsGoalDTOList.add(periodicalSavingsGoalMapper.periodicalSavingsGoalEntityToDTO(sg));
        }
        return periodicalSavingsGoalDTOList;
    }

    public boolean createPeriodicalSavingsGoal(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        PeriodicalSavingsGoal periodicalSavingsGoal = periodicalSavingsGoalMapper.periodicalSavingsGoalDTOtoEntity(periodicalSavingsGoalDTO);
        periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
        return true;
    }

    public boolean deletePeriodicalSavingsGoal(String id) {
        PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
        if (psg == null)
            return false;
        periodicalSavingsGoalRepository.delete(psg);
        return true;
    }

    public PeriodicalSavingsGoalDTO getPeriodicalSavingsGoal(String id) {
        PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
        return periodicalSavingsGoalMapper.periodicalSavingsGoalEntityToDTO(psg);
    }

    private PeriodicalSavingsGoal findPeriodicalSavingsGoalById(String id) {
        for (PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()) {
            if (sg.getId().toString().equals(id)) {
                return sg;
            }
        }
        return null;
    }

    private OneTimeTransactionDto addNewScheduledTransaction(PeriodicalSavingsGoal periodicalSavingsGoal) {
        UUID id = UUID.randomUUID();
        List<UUID> transactionIds = periodicalSavingsGoal.getTransactionIds();
        int numberOfTransactions = periodicalSavingsGoal.getTransactionIds().size();

        //get oldest transaction
        OneTimeTransactionDto oldestTransaction = getTransaction(transactionIds.get(numberOfTransactions - 1).toString());
        if(oldestTransaction == null || oldestTransaction.getAmount()==null || oldestTransaction.getAmount().getAmount() == null)
            return null;
        //check if finished
        if ((numberOfTransactions + 1) * oldestTransaction.getAmount().getAmount() >= periodicalSavingsGoal.getGoal().getAmount()) {
            periodicalSavingsGoal.setAchievementStatus(AchievementStatus.ACHIEVED);
            return null;
        }
        periodicalSavingsGoal.setAchievementStatus(AchievementStatus.IN_PROGRESS);


        final var newOneTimeTransactionDto = OneTimeTransactionDto.builder()
                .description(oldestTransaction.getDescription())
                .id(id)
                .date(LocalDate.now().toString())
                .name(oldestTransaction.getName() + numberOfTransactions + "")
                .type(oldestTransaction.getType())
                .amount(oldestTransaction.getAmount()).build();

        transactionIds.add(id);
        periodicalSavingsGoal.setTransactionIds(transactionIds);

        return newOneTimeTransactionDto;
    }


    private OneTimeTransactionDto getTransaction(String id) {
        Optional<OneTimeTransactionDto> oneTimeTransactionDto = transactionClient.transactionsOnetimeIdGet(id);

        if (oneTimeTransactionDto.isEmpty()) { //TODO what to do if the oneTimeTransactionDto doesn't exist
            System.out.println("transactionDto does not exist");
            return null;
        }
        return oneTimeTransactionDto.get();
    }


    /**
     * Runs every day at 6am.
     */
    @Scheduled(cron = "0 0 6 * * *", zone = Configuration.TIME_ZONE)
    public void transferRecurringTransactionsOnDueDate() {
        List<PeriodicalSavingsGoal> periodicalSavingsGoals = periodicalSavingsGoalRepository.findAll();
        for (PeriodicalSavingsGoal psg : periodicalSavingsGoals) {
            if (checkDue(psg)) {
                try {
                    kafkaSendMessages.sendScheduledMessage(addNewScheduledTransaction(psg));
                } catch (Exception e) {
                    System.out.println(e + "\n Couldn't send the transaction");
                }
            }
        }
    }

    private boolean checkDue(PeriodicalSavingsGoal periodicalSavingsGoal) {
        Duration duration = periodicalSavingsGoal.getDuration();
        Periodicity periodicity = periodicalSavingsGoal.getPeriodicity();
        List<UUID> tids = periodicalSavingsGoal.getTransactionIds();
        OneTimeTransactionDto newestTransaction = getTransaction(tids.get(tids.size() - 1).toString());
        LocalDate lastDate = SavingsGoalMapper.getTimeFromString(newestTransaction.getDate());
        LocalDate nextDate = lastDate.plusMonths(periodicityToInt(periodicity));
        if (nextDate.isAfter(LocalDate.now()) && nextDate.isBefore(LocalDate.now().plusDays(1)) && nextDate.isBefore(duration.getEnd()) && nextDate.isAfter(duration.getStart())) {
            //if its after now and before tomorrow
            return true;
        }
        return false;
    }

    private int periodicityToInt(Periodicity periodicity) {
        int value = 1;
        switch (periodicity) {
            case MONTHLY -> value = 1;
            case YEARLY -> value = 12;
            case QUARTERLY -> value = 3;
            case HALF_YEARLY -> value = 6;
        }
        return value;
    }
}

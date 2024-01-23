package com.financetracker.savingsgoal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.financetracker.savingsgoal.Time.Configuration;
import com.financetracker.savingsgoal.client.BankAccountClient;
import com.financetracker.savingsgoal.client.TransactionClient;
import com.financetracker.savingsgoal.kafka.KafkaRessource;
import com.financetracker.savingsgoal.model.SavingsGoalMapper;
import org.openapitools.client.model.*;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.Periodicity;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingsGoalService {

    private final SavingsGoalFactory savingsGoalFactory;
    private final BankAccountClient bankAccountClient;
    private final TransactionClient transactionClient;
    private final PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;
    private final RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository;
    private final SavingsGoalMapper savingsGoalMapper;
    //private final KafkaRessource kafkaRessource;

 public List<PeriodicalSavingsGoalDTO> getPeriodicalSavingsGoals(){
     List<PeriodicalSavingsGoalDTO> periodicalSavingsGoalDTOList = new ArrayList<>();
     for(PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()){
         periodicalSavingsGoalDTOList.add(savingsGoalMapper.periodicalSavingsGoalEntityToDTO(sg));
     }
     return periodicalSavingsGoalDTOList;
 }
 public boolean createPeriodicalSavingsGoal(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO){
     PeriodicalSavingsGoal periodicalSavingsGoal = savingsGoalMapper.periodicalSavingsGoalDTOtoEntity(periodicalSavingsGoalDTO);
     periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
     return true;
 }

public boolean deletePeriodicalSavingsGoal(String id){
    PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
    if(psg == null)
        return false;
    periodicalSavingsGoalRepository.delete(psg);
    return true;
}

public PeriodicalSavingsGoalDTO getPeriodicalSavingsGoal(String id){
    PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
    return savingsGoalMapper.periodicalSavingsGoalEntityToDTO(psg);
}

private PeriodicalSavingsGoal findPeriodicalSavingsGoalById(String id){
    for(PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()){
        if(sg.getId().toString().equals(id)){
            return sg;
        }
    }
    return null;
}

//-----------

    public List<RuleBasedSavingsGoalDTO> getRuleBasedSavingsGoals(){
        System.out.println("getting all the rule based savings goals");
        List<RuleBasedSavingsGoalDTO> ruleBasedSavingsGoalDTOS = new ArrayList<>();
        ruleBasedSavingsGoalRepository.findAll().forEach(rbsg
                -> ruleBasedSavingsGoalDTOS.add(savingsGoalMapper.ruleBasedSavingsGoalEntityToDTO(rbsg)));
        return ruleBasedSavingsGoalDTOS;
    }
    public boolean createRuleBasedSavingsGoal(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO){
        ruleBasedSavingsGoalRepository.save(savingsGoalMapper.ruleBasedSavingsGoalDTOtoEntity(ruleBasedSavingsGoalDTO));
        System.out.println("rule based savings goal has been created");
        return true;
    }

    public boolean deleteRuleBasedSavingsGoal(String id){
        RuleBasedSavingsGoal ruleBasedSavingsGoal = findRuleBasedSavingsGoalById(id);
        if(ruleBasedSavingsGoal == null)
            return false;

        ruleBasedSavingsGoalRepository.delete(ruleBasedSavingsGoal);
        return true;
    }

    public RuleBasedSavingsGoalDTO getRuleBasedSavingsGoal(String id){
        RuleBasedSavingsGoal ruleBasedSavingsGoal = findRuleBasedSavingsGoalById(id);
        return savingsGoalMapper.ruleBasedSavingsGoalEntityToDTO(ruleBasedSavingsGoal);
    }

    private RuleBasedSavingsGoal findRuleBasedSavingsGoalById(String id){
        for(RuleBasedSavingsGoal sg : ruleBasedSavingsGoalRepository.findAll()){
            if(sg.getId().toString().equals(id)){
                return sg;
            }
        }
        return null;
    }

    public PeriodicalSavingsGoalDTO createRandomPeriodicalSavingsGoal(){
        com.financetracker.savingsgoal.MonetaryAmount newMonetaryAmount = new com.financetracker.savingsgoal.MonetaryAmount();
        newMonetaryAmount.setAmount(Math.random()*10000);
        Duration duration = new Duration();
        duration.setStart(LocalDate.now());
        duration.setEnd(LocalDate.of(2025,10,5));

     PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO = new PeriodicalSavingsGoalDTO();

        periodicalSavingsGoalDTO.setId(UUID.randomUUID());
        periodicalSavingsGoalDTO.setName("random Name");
        periodicalSavingsGoalDTO.setDescription("random description");
        periodicalSavingsGoalDTO.setAchievementStatus(AchievementStatus.ACHIEVED);
        periodicalSavingsGoalDTO.setBankAccountID(UUID.randomUUID());
        periodicalSavingsGoalDTO.setGoal(SavingsGoalMapper.monetaryAmountModeltoDTO(newMonetaryAmount));
        periodicalSavingsGoalDTO.setRecurringRate(newMonetaryAmount.getAmount());
        periodicalSavingsGoalDTO.setRecurringAmount(SavingsGoalMapper.monetaryAmountModeltoDTO(newMonetaryAmount));
        periodicalSavingsGoalDTO.setDuration(SavingsGoalMapper.durationToString(duration));
        periodicalSavingsGoalDTO.setPeriodicity(org.openapitools.model.Periodicity.MONTHLY);
        periodicalSavingsGoalDTO.setOneTimeTransactionRecord(new ArrayList<>());
        return periodicalSavingsGoalDTO;
    }
    public RuleBasedSavingsGoalDTO createRandomRBasedSavingsGoal(){
        RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = new RuleBasedSavingsGoalDTO();
        ruleBasedSavingsGoalDTO.setId(UUID.randomUUID());
        ruleBasedSavingsGoalDTO.setName("name");
        ruleBasedSavingsGoalDTO.setDescription("description");
        ruleBasedSavingsGoalDTO.setType(RuleBasedSavingsGoalDTO.TypeEnum.ANY);
        ruleBasedSavingsGoalDTO.setRules(new ArrayList<>());
        return ruleBasedSavingsGoalDTO;
    }

    //-------[logic - rule based]-----

    //TODO where does this get called?
    public void matchRuleBasedSavingsGoal(RuleBasedSavingsGoal ruleBasedSavingsGoal){
        boolean rulesMatch = false;
        switch(ruleBasedSavingsGoal.getMatchingType()){
            case MATCH_ALL ->rulesMatch = matchAllRules(ruleBasedSavingsGoal);
            case MATCH_ANY -> rulesMatch = matchAnyRule(ruleBasedSavingsGoal);

        }
        if(rulesMatch) ruleBasedSavingsGoal.setAchievementStatus(AchievementStatus.ACHIEVED);
        if(!rulesMatch) ruleBasedSavingsGoal.setAchievementStatus(AchievementStatus.FAILED);
    }

    private boolean matchAnyRule(RuleBasedSavingsGoal ruleBasedSavingsGoal){
        boolean match = false;
        for(Rule rule : ruleBasedSavingsGoal.getRules()){
            if(matchRule(rule)) return true;
        }
        return match;
    }
    private boolean matchAllRules(RuleBasedSavingsGoal ruleBasedSavingsGoal){
        boolean match = true;
        for(Rule rule : ruleBasedSavingsGoal.getRules()){
            if(!matchRule(rule)) match = false;
        }
        return match;
    }

    private boolean matchRule(Rule rule){
        //get BankAccount
        double money = getBankAccountBalance(rule.getBankAccountID().toString());
        return matchRuleType(rule, money);
    }
    private boolean matchRuleType(Rule rule, double money){
        boolean match = false;
        MonetaryAmount targetAmount = rule.getTarget();
        switch (rule.getRuleType()){
            case GREATER_THAN -> match = money > targetAmount.getAmount();
            case EQUALS -> match = money == targetAmount.getAmount();
            case LESS_THAN -> match = money < targetAmount.getAmount();
        }
        return match;
    }

    // ------ [periodical rule logic]

    //TODO call this method periodically after savings goal rules
    public OneTimeTransactionDto addNewPercentageOneTimeTransactionDto(PeriodicalSavingsGoal periodicalSavingsGoal){
     if(periodicalSavingsGoal.getTransactionIds().isEmpty()){ //if no entry present
         periodicalSavingsGoal.setTransactionIds(new ArrayList<>());
         OneTimeTransactionDto ottdto = createPercentageOneTimeTransactionDto(periodicalSavingsGoal);
         periodicalSavingsGoalRepository.save(periodicalSavingsGoal);

         return ottdto;
     }
     periodicalSavingsGoalRepository.delete(periodicalSavingsGoal);
     OneTimeTransactionDto ottd = addNewTransactionDto(periodicalSavingsGoal);
     periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
     return ottd;
    }

    //TODO call this method periodically after savings goal rules
    public OneTimeTransactionDto addNewAmountBasedOneTimeTransactionDto(PeriodicalSavingsGoal periodicalSavingsGoal){
        if(periodicalSavingsGoal.getTransactionIds().isEmpty()){ //if no entry present
            periodicalSavingsGoal.setTransactionIds(new ArrayList<>());
            OneTimeTransactionDto ottdto =  createAmountBasedOneTimeTransactionDto(periodicalSavingsGoal);
            periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
            return ottdto;
        }
        periodicalSavingsGoalRepository.delete(periodicalSavingsGoal);
        OneTimeTransactionDto ottd = addNewTransactionDto(periodicalSavingsGoal);
        periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
        return ottd;
    }

    private OneTimeTransactionDto addNewTransactionDto(PeriodicalSavingsGoal periodicalSavingsGoal){
        OneTimeTransactionDto newOneTimeTransactionDto = new OneTimeTransactionDto();
        UUID id = UUID.randomUUID();

        List<UUID> transactionIds = periodicalSavingsGoal.getTransactionIds();
        int numberOfTransactions = periodicalSavingsGoal.getTransactionIds().size();
        //get oldest transaction
        OneTimeTransactionDto oldestTransaction = getTransaction(transactionIds.get(numberOfTransactions-1).toString());

        //check if finished
        if((numberOfTransactions+1)*oldestTransaction.getAmount().getAmount() >= periodicalSavingsGoal.getGoal().getAmount()){
            periodicalSavingsGoal.setAchievementStatus(AchievementStatus.ACHIEVED);
            return null;
        }else{
            periodicalSavingsGoal.setAchievementStatus(AchievementStatus.IN_PROGRESS);
        }

        newOneTimeTransactionDto.setDescription(oldestTransaction.getDescription());
        newOneTimeTransactionDto.setId(id);
        newOneTimeTransactionDto.setDate(LocalDate.now().toString());
        newOneTimeTransactionDto.setName(oldestTransaction.getName() + numberOfTransactions + "");
        newOneTimeTransactionDto.setType(oldestTransaction.getType());
        newOneTimeTransactionDto.setAmount(oldestTransaction.getAmount());

        transactionIds.add(id);
        periodicalSavingsGoal.setTransactionIds(transactionIds);

        return newOneTimeTransactionDto;
    }

//TODO initial call when creating
    private OneTimeTransactionDto createPercentageOneTimeTransactionDto(PeriodicalSavingsGoal periodicalSavingsGoal){
        double bankAccountBalance = getBankAccountBalance(periodicalSavingsGoal.getBankAccountId().toString());
        double percentage = periodicalSavingsGoal.getRecurringRate().getAmount();
        double amount = 0.0, months = 0.0;
        MonetaryAmountDto transferAmount = new MonetaryAmountDto();

        Duration duration = new Duration();
        duration.setStart(LocalDate.now());

        if(periodicalSavingsGoal.getRecurringRate().getAmount()!=0.0 && bankAccountBalance != 0.0 ) {
            amount = bankAccountBalance * percentage;
            months = Math.ceil(bankAccountBalance/amount); // rounded up for long conversion
            duration.setEnd(LocalDate.now().plusMonths((long) months));
        }else{
            duration.setEnd(LocalDate.now());
        }

        transferAmount.setAmount(amount);
        UUID id = UUID.randomUUID();
        ArrayList<UUID> transaction = new ArrayList();
        transaction.add(id);
        periodicalSavingsGoal.setTransactionIds(transaction);

        OneTimeTransactionDto oneTimeTransactionDto = new OneTimeTransactionDto();
        oneTimeTransactionDto.setAmount(transferAmount);
        oneTimeTransactionDto.setDescription(periodicalSavingsGoal.getDescription());
        oneTimeTransactionDto.setId(id);
        oneTimeTransactionDto.setName(periodicalSavingsGoal.getName());
        oneTimeTransactionDto.setType(TypeDto.SHIFT);
        oneTimeTransactionDto.setDate(LocalDate.now().toString());


        return oneTimeTransactionDto;
    }

    //TODO initial call when creating
    private OneTimeTransactionDto createAmountBasedOneTimeTransactionDto(PeriodicalSavingsGoal periodicalSavingsGoal){
        MonetaryAmount emptyAmount = new MonetaryAmount();
        MonetaryAmountDto moneyToBeTransfered = new MonetaryAmountDto();

        periodicalSavingsGoal.setRecurringRate(emptyAmount);
        moneyToBeTransfered.setAmount(periodicalSavingsGoal.getRecurringRate().getAmount());

        UUID id = UUID.randomUUID();
        ArrayList<UUID> transactionIds = new ArrayList<>();
        transactionIds.add(id);
        periodicalSavingsGoal.setTransactionIds(transactionIds);

        OneTimeTransactionDto oneTimeTransactionDto = new OneTimeTransactionDto();
        oneTimeTransactionDto.setAmount(moneyToBeTransfered);
        oneTimeTransactionDto.setName(periodicalSavingsGoal.getName());
        oneTimeTransactionDto.setDate(LocalDate.now().toString());
        oneTimeTransactionDto.setId(id);
        oneTimeTransactionDto.setDescription(periodicalSavingsGoal.getDescription());

        return oneTimeTransactionDto;
    }

    private double getBankAccountBalance(String id){
        Optional<BankAccountDto> bankAccountDto = bankAccountClient.getBankAccount(id);
        double money = 0.0;

        if(bankAccountDto.isEmpty()) { //TODO what to do if the bank account doesn'T exist
            System.out.println("bank account does not exist");
            return money;
        }
        BankAccountDto bankAccount = bankAccountDto.get();
        MonetaryAmountDto balance = bankAccount.getBalance();
        try{
            money = balance.getAmount();
            return money;
        }catch (Exception e){
            System.out.println("No Monetary amount provided");
        }
        return money;
    }

    private OneTimeTransactionDto getTransaction(String id){
        Optional<OneTimeTransactionDto> oneTimeTransactionDto = transactionClient.transactionsOnetimeIdGet(id);


        if(oneTimeTransactionDto.isEmpty()) { //TODO what to do if the oneTimeTransactionDto doesn't exist
            System.out.println("transactionDto does not exist");
            return null;
        }
        OneTimeTransactionDto transactionDto = oneTimeTransactionDto.get();
        return transactionDto;
    }


    //TODO call the one periodical after clock
    //TODO new transaction what happens?

    public void receivedNewTransaction(OneTimeTransactionDto oneTimeTransactionDto){
     //Check if rule based SavingsGoals still hold
     List<RuleBasedSavingsGoal> lRbsg = ruleBasedSavingsGoalRepository.findAll();
     lRbsg.forEach(this::matchRuleBasedSavingsGoal);

     //TODO Check also the other ones


    }

    /**
     * Runs every day at 6am.
     */
    @Scheduled(cron = "0 0 6 * * *", zone = Configuration.TIME_ZONE)
    public void transferRecurringTransactionsOnDueDate() {
        List<PeriodicalSavingsGoal> periodicalSavingsGoals = periodicalSavingsGoalRepository.findAll();
        for (PeriodicalSavingsGoal psg : periodicalSavingsGoals){
            if(checkDue(psg)){
                try {
                    //kafkaRessource.sendMessage(addNewTransactionDto(psg));//TODO send periodicalSavingsGoals in a qeue [done] - check if it works
                }catch (Exception e){
                    System.out.println(e);
                    System.out.println("Couldn't send the transaction");
                }

            }
        }
    }

    private boolean checkDue(PeriodicalSavingsGoal periodicalSavingsGoal){
        Duration duration = periodicalSavingsGoal.getDuration();
        Periodicity periodicity = periodicalSavingsGoal.getPeriodicity();
        List<UUID> tids= periodicalSavingsGoal.getTransactionIds();
        OneTimeTransactionDto newestTransaction = getTransaction(tids.get(tids.size()-1).toString());
        LocalDate lastDate = savingsGoalMapper.getTimeFromString(newestTransaction.getDate());
        LocalDate nextDate = lastDate.plusMonths(periodicityToInt(periodicity));
        if(nextDate.isAfter(LocalDate.now()) && nextDate.isBefore(LocalDate.now().plusDays(1))){
            //if its after now and before tomorrow
            return true;
        }

        return false;
    }

    private int periodicityToInt(Periodicity periodicity){
        int value = 1;
        switch (periodicity){
            case MONTHLY -> value = 1;
            case YEARLY -> value = 12;
            case QUARTERLY -> value = 3;
            case HALF_YEARLY -> value = 6;
        }
        return value;
    }
}

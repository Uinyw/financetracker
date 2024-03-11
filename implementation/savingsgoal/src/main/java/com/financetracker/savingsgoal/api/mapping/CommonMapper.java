package com.financetracker.savingsgoal.api.mapping;

import com.financetracker.savingsgoal.logic.model.AchievementStatus;
import com.financetracker.savingsgoal.logic.model.MonetaryAmount;
import org.openapitools.model.AchievementStatusDto;
import org.openapitools.model.MonetaryAmountDto;
import org.springframework.stereotype.Component;

@Component
public class CommonMapper {

    public MonetaryAmountDto monetaryAmountModelToDto(final MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null || monetaryAmount.getAmount() == null) {
            return null;
        }

        return MonetaryAmountDto.builder().amount(monetaryAmount.getAmount()).build();
    }

    public MonetaryAmount monetaryAmountDtoToModel(final MonetaryAmountDto monetaryAmountDto) {
        if (monetaryAmountDto == null || monetaryAmountDto.getAmount() == null) {
            return null;
        }

        return new MonetaryAmount(monetaryAmountDto.getAmount());
    }

    public AchievementStatusDto achievementStatusModelToDto(final AchievementStatus achievementStatus) {
        return switch (achievementStatus) {
            case IN_PROGRESS -> AchievementStatusDto.IN_PROGRESS;
            case FAILED -> AchievementStatusDto.FAILED;
            case ACHIEVED -> AchievementStatusDto.ACHIEVED;
        };
    }

    public AchievementStatus achievementStatusDtoToModel(final AchievementStatusDto achievementStatusDto) {
        return switch (achievementStatusDto) {
            case IN_PROGRESS -> AchievementStatus.IN_PROGRESS;
            case FAILED -> AchievementStatus.FAILED;
            case ACHIEVED -> AchievementStatus.ACHIEVED;
        };
    }

}

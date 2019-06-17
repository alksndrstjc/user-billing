package com.company.userbilling.services.calculators;

import com.company.userbilling.domain.User;
import com.company.userbilling.services.dto.BillingPostDto;
import com.company.userbilling.services.dto.BoughtItemDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PercentageBasedDiscountCalculator implements DiscountCalculator {

    private static final double NO_DISCOUNT = 1;

    private final UserBasedDiscountRules discountRules;

    @Override
    public double calculateDiscount(BillingPostDto billing) {

        User user = billing.getUser();
        List<BoughtItemDto> boughtItems = billing.getBoughtItems();

        double percentageBasedDiscountForATypeOfUser = discountRules.findBestPossibleDiscountForUser(user);

        double totalPercentage = 0;
        double itemCount = 0;

        for (BoughtItemDto boughtItem : boughtItems) {
            double currentPercentage = percentageBasedDiscountForATypeOfUser;
            if (boughtItem.isGrocery()) {
                currentPercentage = NO_DISCOUNT;
            }

            itemCount += boughtItem.getNum();
            totalPercentage += currentPercentage * itemCount;
        }

        double avrgPercentageDeduction = totalPercentage / itemCount;
        avrgPercentageDeduction = avrgPercentageDeduction == 1 ? 0 : avrgPercentageDeduction;

        return billing.getGrossSum() * avrgPercentageDeduction;
    }

}

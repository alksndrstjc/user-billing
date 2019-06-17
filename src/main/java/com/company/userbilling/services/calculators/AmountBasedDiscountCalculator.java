package com.company.userbilling.services.calculators;

import com.company.userbilling.services.dto.BillingPostDto;

public class AmountBasedDiscountCalculator implements DiscountCalculator {

    private static final int DISCOUNT_ADDED_LIMIT = 100;
    private static final int DISCOUNT_TO_ADD = 5;

    @Override
    public double calculateDiscount(BillingPostDto billing) {
        double grossSum = billing.getGrossSum();

        double discount = 0;
        while (grossSum > DISCOUNT_ADDED_LIMIT) {
            discount += DISCOUNT_TO_ADD;
            grossSum -= DISCOUNT_ADDED_LIMIT;
        }
        return discount;
    }
}

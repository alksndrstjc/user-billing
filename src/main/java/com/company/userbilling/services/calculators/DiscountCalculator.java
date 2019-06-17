package com.company.userbilling.services.calculators;

import com.company.userbilling.services.dto.BillingPostDto;

public interface DiscountCalculator {

    double calculateDiscount(BillingPostDto billing);
}

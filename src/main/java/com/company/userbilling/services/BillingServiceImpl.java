package com.company.userbilling.services;

import com.company.userbilling.services.calculators.AmountBasedDiscountCalculator;
import com.company.userbilling.services.calculators.DiscountCalculator;
import com.company.userbilling.services.calculators.PercentageBasedDiscountCalculator;
import com.company.userbilling.services.calculators.UserBasedDiscountRules;
import com.company.userbilling.services.dto.BillingPostDto;
import com.company.userbilling.services.dto.BillingResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class BillingServiceImpl implements BillingService {

    private final List<DiscountCalculator> discountProcessors = asList(
            new PercentageBasedDiscountCalculator(new UserBasedDiscountRules()),
            new AmountBasedDiscountCalculator());

    @Override
    public BillingResponseDto determineNetAmount(BillingPostDto billingPostDto) {
        double grossAmount = billingPostDto.getGrossSum();
        double totalDiscount = 0;
        for (DiscountCalculator processor : discountProcessors) {
            totalDiscount += processor.calculateDiscount(billingPostDto);
        }
        double netAmount = grossAmount - totalDiscount;

        return new BillingResponseDto(grossAmount, netAmount, totalDiscount);
    }

}

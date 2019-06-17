package com.company.userbilling.services;

import com.company.userbilling.services.dto.BillingPostDto;
import com.company.userbilling.services.dto.BillingResponseDto;

public interface BillingService {

    BillingResponseDto determineNetAmount(BillingPostDto billingPostDto);

}
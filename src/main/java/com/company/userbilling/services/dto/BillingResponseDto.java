package com.company.userbilling.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillingResponseDto {
    double grossAmount;
    double netAmount;
    double totalDiscount;
}

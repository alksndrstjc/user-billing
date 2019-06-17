package com.company.userbilling.services.dto;

import com.company.userbilling.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class BillingPostDto {
    @Valid
    private User user;
    @Valid
    private List<BoughtItemDto> boughtItems;
    @JsonIgnore
    private double grossSum;

    public BillingPostDto(User user, List<BoughtItemDto> boughtItems) {
        this.user = user;
        this.boughtItems = boughtItems;
    }

    public double getGrossSum() {
        return grossSum = boughtItems.stream().mapToDouble(boughtItem -> boughtItem.price * boughtItem.num).sum();
    }
}

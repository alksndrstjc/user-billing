package com.company.userbilling.services.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BoughtItemDto {
    @NotNull
    protected int num;
    @NotNull
    protected String name;
    @NotNull
    protected double price;
    @NotNull
    protected boolean isGrocery;
}

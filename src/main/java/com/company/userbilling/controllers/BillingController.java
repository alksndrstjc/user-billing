package com.company.userbilling.controllers;

import com.company.userbilling.services.BillingService;
import com.company.userbilling.services.dto.BillingPostDto;
import com.company.userbilling.services.dto.BillingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BillingController {

    private final BillingService service;

    @PostMapping("/billing")
    ResponseEntity<BillingResponseDto> determineNetAmount(@Valid @RequestBody BillingPostDto dto) {
        BillingResponseDto netAmount = service.determineNetAmount(dto);
        return new ResponseEntity<>(netAmount, HttpStatus.OK);
    }

}

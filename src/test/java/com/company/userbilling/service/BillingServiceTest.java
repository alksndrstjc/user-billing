package com.company.userbilling.service;

import com.company.userbilling.domain.User;
import com.company.userbilling.domain.UserType;
import com.company.userbilling.services.BillingServiceImpl;
import com.company.userbilling.services.dto.BillingPostDto;
import com.company.userbilling.services.dto.BillingResponseDto;
import com.company.userbilling.services.dto.BoughtItemDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillingServiceTest {

    @Autowired
    BillingServiceImpl billingService;


    @Test
    public void regularCustomerNoDiscountsExpectFullPrice() {
        User user = new User();
        user.setCreatedOn(OffsetDateTime.now());
        user.setUsername("Aleksandar");
        user.setUserTypes(asList(UserType.CUSTOMER));

        BoughtItemDto boughtItem = new BoughtItemDto();
        boughtItem.setNum(2);
        boughtItem.setName("TV");
        boughtItem.setGrocery(false);
        boughtItem.setPrice(50);

        BillingPostDto dto = new BillingPostDto(user, asList(boughtItem));
        BillingResponseDto netAmount = billingService.determineNetAmount(dto);

        assertEquals(netAmount.getNetAmount(), 100, 0);
    }

    @Test
    public void affiliateCustomer() {
        User user = new User();
        user.setCreatedOn(OffsetDateTime.now());
        user.setUsername("Aleksandar");
        user.setUserTypes(asList(UserType.AFFILIATE));

        BoughtItemDto boughtItem = new BoughtItemDto();
        boughtItem.setNum(1);
        boughtItem.setName("TV");
        boughtItem.setGrocery(false);
        boughtItem.setPrice(50);

        BillingPostDto dto = new BillingPostDto(user, asList(boughtItem));

        BillingResponseDto netAmount = billingService.determineNetAmount(dto);
        assertEquals(netAmount.getNetAmount(), 45, 0);
    }

    @Test
    public void employeeCustomer() {
        User user = new User();
        user.setCreatedOn(OffsetDateTime.now());
        user.setUsername("Aleksandar");
        user.setUserTypes(asList(UserType.EMPLOYEE));

        BoughtItemDto boughtItem = new BoughtItemDto();
        boughtItem.setNum(1);
        boughtItem.setName("TV");
        boughtItem.setGrocery(false);
        boughtItem.setPrice(50);

        BillingPostDto dto = new BillingPostDto(user, asList(boughtItem));
        BillingResponseDto netAmount = billingService.determineNetAmount(dto);

        assertEquals(netAmount.getNetAmount(), 35, 0);
    }

    @Test
    public void employeeCustomerMoreThanHundredBalance() {
        User user = new User();
        user.setCreatedOn(OffsetDateTime.now());
        user.setUsername("Aleksandar");
        user.setUserTypes(asList(UserType.EMPLOYEE));

        BoughtItemDto boughtItem = new BoughtItemDto();
        boughtItem.setNum(4);
        boughtItem.setName("TV");
        boughtItem.setGrocery(false);
        boughtItem.setPrice(50);


        BillingPostDto dto = new BillingPostDto(user, asList(boughtItem));
        BillingResponseDto netAmount = billingService.determineNetAmount(dto);
        assertEquals(netAmount.getNetAmount(), 135, 0);
    }

    @Test
    public void employeeBuysGroceriesMoreThanHundredBalanceNoEmployeeDiscount() {
        User user = new User();
        user.setCreatedOn(OffsetDateTime.now());
        user.setUsername("Aleksandar");
        user.setUserTypes(asList(UserType.EMPLOYEE));

        BoughtItemDto boughtItem = new BoughtItemDto();
        boughtItem.setNum(4);
        boughtItem.setName("Food");
        boughtItem.setGrocery(true);
        boughtItem.setPrice(100);

        BillingPostDto dto = new BillingPostDto(user, asList(boughtItem));
        BillingResponseDto netAmount = billingService.determineNetAmount(dto);
        assertEquals(netAmount.getNetAmount(), 385, 0);
    }

    @Test
    public void regularAtLeastTwoYearsOldCustomerBuysTv() {
        User user = new User();
        user.setCreatedOn(OffsetDateTime.MIN);
        user.setUsername("Aleksandar");
        user.setUserTypes(asList(UserType.CUSTOMER));

        BoughtItemDto boughtItem = new BoughtItemDto();
        boughtItem.setNum(1);
        boughtItem.setName("TV");
        boughtItem.setGrocery(false);
        boughtItem.setPrice(50);

        BillingPostDto dto = new BillingPostDto(user, asList(boughtItem));
        BillingResponseDto netAmount = billingService.determineNetAmount(dto);

        assertEquals(netAmount.getNetAmount(), 47.5, 0);
    }
}

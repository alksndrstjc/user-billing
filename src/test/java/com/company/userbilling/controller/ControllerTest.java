package com.company.userbilling.controller;

import com.company.userbilling.controllers.BillingController;
import com.company.userbilling.domain.User;
import com.company.userbilling.services.BillingService;
import com.company.userbilling.services.dto.BillingPostDto;
import com.company.userbilling.services.dto.BillingResponseDto;
import com.company.userbilling.services.dto.BoughtItemDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BillingController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BillingService service;


    @Test
    public void test() throws Exception {
        User user = new User();
        user.setCreatedOn(OffsetDateTime.now());
        user.setUsername("Aleksandar");
        user.setUserTypes(emptyList());

        BoughtItemDto boughtItem = new BoughtItemDto();
        boughtItem.setNum(2);
        boughtItem.setName("TV");
        boughtItem.setGrocery(false);
        boughtItem.setPrice(50);

        BillingPostDto dto = new BillingPostDto(user, asList(boughtItem));

        given(service.determineNetAmount(dto)).willReturn(new BillingResponseDto(100, 100, 0));

        String json = "{\"boughtItems\":[{\"grocery\":false,\"name\":\"TV\",\"num\":2,\"price\":50}],\"user\":{\"createdOn\":\"2019-06-14T14:00:01.457Z\",\"userTypes\":[], \"username\":\"Aleksandar\"}}";

        mvc.perform(post("/api/v1/billing")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

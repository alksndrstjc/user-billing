package com.company.userbilling.domain;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

// not a real domain class as there is no persistence
@Data
public class User {
    private List<UserType> userTypes;
    private String username;
    @NotNull
    private OffsetDateTime createdOn;
}

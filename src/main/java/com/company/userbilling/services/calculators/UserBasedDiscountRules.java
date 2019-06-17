package com.company.userbilling.services.calculators;

import com.company.userbilling.domain.User;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.company.userbilling.domain.UserType.*;


@AllArgsConstructor
public class UserBasedDiscountRules {

    private static final double EMPLOYEE_DISCOUNT = 0.3;
    private static final double AFFILIATE_DISCOUNT = 0.1;
    private static final double TWO_PLUS_YEARS_DISCOUNT = 0.05;
    private static final double NO_DISCOUNT = 1;

    private static final Map<Predicate<User>, Double> rule = new LinkedHashMap<>();

    //this would be returned from db
    static {
        rule.put(user -> user.getUserTypes().contains(EMPLOYEE), EMPLOYEE_DISCOUNT);
        rule.put(user -> user.getUserTypes().contains(AFFILIATE), AFFILIATE_DISCOUNT);
        rule.put(user -> user.getUserTypes().contains(CUSTOMER) && ChronoUnit.YEARS.between(user.getCreatedOn(), OffsetDateTime.now()) >= 2, TWO_PLUS_YEARS_DISCOUNT);
    }

    public double findBestPossibleDiscountForUser(User user) {
        for (Map.Entry<Predicate<User>, Double> entry : rule.entrySet()) {
            if (entry.getKey().test(user)) {
                return entry.getValue();
            }
        }
        return NO_DISCOUNT;
    }


}

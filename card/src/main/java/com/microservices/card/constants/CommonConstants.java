package com.microservices.card.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {

    public static final String STATUS_201 = "201";
    public static final String STATUS_202 = "202";

    public static final String INVALID_VALIDATE = "Data invalid";

    public static final Integer NEW_CARD_LIMIT = 100_000;

    public static final String MESSAGE_CREATED = "Card created successfully";
    public static final String MESSAGE_UPDATED = "Card updated successfully";
    public static final String MESSAGE_DELETED = "Card deleted successfully";

//    public static String getErrorCode(String code, Object... args) {
//        return MessageFormat.format(code, args);
//    }
}

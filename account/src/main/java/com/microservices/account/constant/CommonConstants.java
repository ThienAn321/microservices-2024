package com.microservices.account.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {

    public static final String STATUS_201 = "201";
    public static final String STATUS_202 = "202";

    public static final String ADDRESS = "123 Main Street, New York";

    public static final String INVALID_VALIDATE = "Data invalid";

    public static final String MESSAGE_CREATED = "Account created successfully";
    public static final String MESSAGE_UPDATED = "Account updated successfully";
    public static final String MESSAGE_DELETED = "Account deleted successfully";


//    public static String getErrorCode(String code, Object... args) {
//        return MessageFormat.format(code, args);
//    }
}

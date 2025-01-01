package com.microservices.gateway.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {

    public static final String CORRELATION_ID = "correlation-id";

//    public static String getErrorCode(String code, Object... args) {
//        return MessageFormat.format(code, args);
//    }
}

package com.microservices.loan.service.dto.response;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "loan")
@Data
public class LoanContactInfoDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}

package com.microservices.account.service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(int httpStatusCode, String message, LocalDateTime errorTime) {
}
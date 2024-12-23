package com.microservices.card.controller;

import com.microservices.card.constants.CommonConstants;
import com.microservices.card.service.CardService;
import com.microservices.card.service.dto.request.CardDto;
import com.microservices.card.service.dto.response.CardContactInfoDto;
import com.microservices.card.service.dto.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@RefreshScope
public class CardController {

    private final CardService cardService;
    private final CardContactInfoDto cardContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @GetMapping("/details")
    public ResponseEntity<CardDto> getCardDetails(@RequestParam String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardService.getCardDetails(mobileNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam String mobileNumber) {
        cardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CommonConstants.STATUS_201, CommonConstants.MESSAGE_CREATED));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@RequestBody @Valid CardDto cardsDto) {
        cardService.updateCard(cardsDto);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(CommonConstants.STATUS_202, CommonConstants.MESSAGE_UPDATED));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam String mobileNumber) {
        cardService.deleteCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(CommonConstants.STATUS_202, CommonConstants.MESSAGE_DELETED));
    }

    @GetMapping("/version")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<CardContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardContactInfoDto);
    }
}

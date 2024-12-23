package com.microservices.card.service;

import com.microservices.card.service.dto.request.CardDto;

public interface CardService {

    CardDto getCardDetails(String mobileNumber);

    void createCard(String mobileNumber);

    void updateCard(CardDto cardDto);

    void deleteCard(String mobileNumber);
}

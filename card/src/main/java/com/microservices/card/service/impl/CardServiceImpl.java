package com.microservices.card.service.impl;

import com.microservices.card.constants.CommonConstants;
import com.microservices.card.entity.Card;
import com.microservices.card.enumeration.CardType;
import com.microservices.card.exception.custom.CardAlreadyExistsException;
import com.microservices.card.exception.custom.DataNotFoundException;
import com.microservices.card.repository.CardRepository;
import com.microservices.card.service.CardService;
import com.microservices.card.service.dto.request.CardDto;
import com.microservices.card.service.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public CardDto getCardDetails(String mobileNumber) {
        return cardRepository.findByMobileNumber(mobileNumber)
                .map(cardMapper::toCardResponseDto)
                .orElseThrow(() -> new DataNotFoundException("Card", "mobileNumber", mobileNumber));
    }

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCards = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber: " + mobileNumber);
        }
        cardRepository.save(this.generatesCardEntity(mobileNumber));
    }

    @Override
    public void updateCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.getCardNumber())
                .orElseThrow(() -> new DataNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
        BeanUtils.copyProperties(cardDto, card);
        cardRepository.save(card);
    }

    @Override
    public void deleteCard(String mobileNumber) {
        Long cardId = cardRepository.findByMobileNumber(mobileNumber)
                .map(Card::getCardId)
                .orElseThrow(() -> new DataNotFoundException("Card", "mobileNumber", mobileNumber));
        cardRepository.deleteById(cardId);
    }

    private Card generatesCardEntity(String mobileNumber) {
        Card card = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomCardNumber));
        card.setMobileNumber(mobileNumber);
        card.setCardType(CardType.CREDIT_CARD);
        card.setTotalLimit(CommonConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CommonConstants.NEW_CARD_LIMIT);
        return card;
    }
}

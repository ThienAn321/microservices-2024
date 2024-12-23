package com.microservices.card.service.mapper;

import com.microservices.card.entity.Card;
import com.microservices.card.service.dto.request.CardDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {

    CardDto toCardResponseDto(Card card);

    Card toEntity(Card cardDto);
}

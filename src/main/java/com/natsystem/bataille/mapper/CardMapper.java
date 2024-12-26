package com.natsystem.bataille.mapper;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "suit", source = "suit")
    CardDTO toDTO(Card card);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "suit", source = "suit")
    Card toEntity(CardDTO cardDTO);

    List<CardDTO> toDTOList(List<Card> cards);
    List<Card> toEntityList(List<CardDTO> cardDTOs);
}

package com.natsystem.bataille.mapper;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CardMapper {

    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    CardDTO toDTO(Card card);

    Card toEntity(CardDTO cardDTO);

    List<CardDTO> toDTOList(List<Card> cards);

    List<Card> toEntityList(List<CardDTO> cardDTOs);
}

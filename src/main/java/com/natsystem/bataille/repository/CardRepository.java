package com.natsystem.bataille.repository;

import com.natsystem.bataille.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findBySuit(String suit);
}

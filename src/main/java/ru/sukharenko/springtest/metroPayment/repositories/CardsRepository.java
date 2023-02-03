package ru.sukharenko.springtest.metroPayment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sukharenko.springtest.metroPayment.models.Cards;

import java.util.Optional;


@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {
    Optional <Cards> findByEmail(String email);
}

package ru.sukharenko.springtest.metroPayment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sukharenko.springtest.metroPayment.models.Cards;



@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {
}

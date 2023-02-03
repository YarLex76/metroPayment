package ru.sukharenko.springtest.metroPayment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sukharenko.springtest.metroPayment.models.BalanceReplenishment;
import ru.sukharenko.springtest.metroPayment.models.Cards;

import java.util.List;
import java.util.Optional;


public interface BalanceReplenishmentRepository extends JpaRepository<BalanceReplenishment, Long> {

   List<BalanceReplenishment> findAllByOwner(Cards owner);
}

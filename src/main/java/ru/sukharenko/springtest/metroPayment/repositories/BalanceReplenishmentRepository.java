package ru.sukharenko.springtest.metroPayment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sukharenko.springtest.metroPayment.models.BalanceReplenishment;


public interface BalanceReplenishmentRepository extends JpaRepository<BalanceReplenishment, Long> {
}

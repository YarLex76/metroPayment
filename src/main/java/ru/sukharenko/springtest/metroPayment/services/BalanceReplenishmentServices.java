package ru.sukharenko.springtest.metroPayment.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sukharenko.springtest.metroPayment.models.BalanceReplenishment;
import ru.sukharenko.springtest.metroPayment.repositories.BalanceReplenishmentRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true) // все публичные методы будут иметь аннотацию @Transactional,
                                // кроме помеченных @Transactional
public class BalanceReplenishmentServices {
    private final BalanceReplenishmentRepository balanceReplenishmentRepository;

    public BalanceReplenishmentServices(BalanceReplenishmentRepository balanceReplenishmentRepository) {
        this.balanceReplenishmentRepository = balanceReplenishmentRepository;
    }

    public List<BalanceReplenishment> findAll(){       // вернет все пополнения балансов
        return balanceReplenishmentRepository.findAll();
    }

    @Transactional
    public void save(BalanceReplenishment balanceReplenishment){
        balanceReplenishment.setDateTrip(new Date()); // добавляем текущее время
        balanceReplenishmentRepository.save(balanceReplenishment);
    }

}

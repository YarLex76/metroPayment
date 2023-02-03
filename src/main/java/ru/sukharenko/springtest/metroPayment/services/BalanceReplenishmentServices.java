package ru.sukharenko.springtest.metroPayment.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sukharenko.springtest.metroPayment.models.BalanceReplenishment;
import ru.sukharenko.springtest.metroPayment.models.Cards;
import ru.sukharenko.springtest.metroPayment.repositories.BalanceReplenishmentRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // все публичные методы будут иметь аннотацию @Transactional,
                                // кроме помеченных @Transactional
public class BalanceReplenishmentServices {
    private final BalanceReplenishmentRepository balanceReplenishmentRepository;

    @Autowired
    public BalanceReplenishmentServices(BalanceReplenishmentRepository balanceReplenishmentRepository) {
        this.balanceReplenishmentRepository = balanceReplenishmentRepository;
    }

    public List<BalanceReplenishment> findAll(){       // вернет все пополнения балансов
        return balanceReplenishmentRepository.findAll();
    }

    public List<BalanceReplenishment> findAllByEmail (Cards owner){       // вернет все пополнения балансов
        return balanceReplenishmentRepository.findAllByOwner(owner);
    }

    public BalanceReplenishment findOne (long id){ // вернем одну карту по id
        Optional<BalanceReplenishment> foundBalanceReplenishment = balanceReplenishmentRepository.findById(id);
        return foundBalanceReplenishment.orElse(null);
    }
    @Transactional
    public void save(BalanceReplenishment balanceReplenishment){
        balanceReplenishment.setDateTrip(new Date()); // добавляем текущее время
        balanceReplenishmentRepository.save(balanceReplenishment);
    }



}

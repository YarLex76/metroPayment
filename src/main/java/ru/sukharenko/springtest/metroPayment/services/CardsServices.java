package ru.sukharenko.springtest.metroPayment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sukharenko.springtest.metroPayment.models.Cards;
import ru.sukharenko.springtest.metroPayment.repositories.CardsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional (readOnly = true) // все публичные методы будут иметь аннотацию @Transactional, кроме помеченных @Transactional
public class CardsServices {

    private final CardsRepository cardsRepository; // объявляем

    @Autowired
    public CardsServices(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public List<Cards> findAll(){       // вернет список всех карт
        return cardsRepository.findAll();
    } // вернет все карты

    public Cards findOne (long id){ // вернем одну карту по id
        Optional<Cards> foundCard = cardsRepository.findById(id);
        return foundCard.orElse(null);
    }

    @Transactional // Будет открыта транзакция т.к. будет происходить изменение в БД
    public void save(Cards card){
        cardsRepository.save(card);
    }

    public int findBalance (String email){ // вернем баланс по емайлу
        Optional<Cards> foundCard = cardsRepository.findByEmail(email);
        return foundCard.get().getCardBalance();
    }







}

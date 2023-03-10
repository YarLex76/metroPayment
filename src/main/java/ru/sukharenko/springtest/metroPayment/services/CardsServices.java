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

    public Cards findOne (String email){ // вернем одну карту по email
        Optional<Cards> foundCard = cardsRepository.findByEmail(email);
        return foundCard.orElse(null);
    }

    @Transactional // Будет открыта транзакция т.к. будет происходить изменение в БД
    public void save(Cards card){
        enrichCard(card); // добавляю недостающие данные, не прешедшие от пользователя
        cardsRepository.save(card);
    }

    @Transactional // Будет открыта транзакция т.к. будет происходить изменение в БД
    public void saveAndUpdate(Cards card){
        cardsRepository.save(card);
    }

    private void enrichCard(Cards card){ // присваиваю стартовое значение баланса == 0
        card.setCardBalance(0);
    }







}

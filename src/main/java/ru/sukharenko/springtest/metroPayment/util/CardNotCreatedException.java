package ru.sukharenko.springtest.metroPayment.util;

// класс для обработки ошибки создания новой карты
public class CardNotCreatedException extends RuntimeException {

    public  CardNotCreatedException (String msg) { // принимаем сообщение об ошибке
        super(msg);
    }
}

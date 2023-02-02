package ru.sukharenko.springtest.metroPayment.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sukharenko.springtest.metroPayment.models.Cards;
import ru.sukharenko.springtest.metroPayment.services.CardsServices;
import ru.sukharenko.springtest.metroPayment.util.CardErrorResponse;
import ru.sukharenko.springtest.metroPayment.util.CardNotCreatedException;

import java.util.List;

@RestController   // @RestController ==  @Controller + @ResponseBody (отдает Json на get запрос)
@RequestMapping("/card")
public class CardsController {

    private final CardsServices cardsServices; // объявляем переменную типа CardsServices для использования его методов

    @Autowired
    public CardsController(CardsServices cardsServices) {
        this.cardsServices = cardsServices;
    } // конструктор для  CardsServices

    @GetMapping("/hello") //http://localhost:8080/card/hello
    public String hello(){
        return "Hello!";
    } // тестовый метод

    @GetMapping("/all") //http://localhost:8080/card/all
    public List<Cards> getCards(){
        return cardsServices.findAll();
    } // вернем список всех карт

    @GetMapping("/{id}") //http://localhost:8080/card/all
    public Cards getCard(@PathVariable("id") long id ){ // вернем одну карту id
        //Cards cards = new Cards(cardsServices.findOne(id));    
        return cardsServices.findOne(id);
    }

    @GetMapping("/email/{email}") //http://localhost:8080/card/all
    public int getEmail(@PathVariable("email") String email){ // вернем одну карту id
        return cardsServices.findBalabce(email);
    }

    @PostMapping ("/new")//http://localhost:8080/card/new
    // метод создания новой карты
    // @RequestBody  - преобразует json в объект класса Cards
    // @Valid - проверит входящие данные на соответствие аннотаций над полями в классе Cards (@NotEmpty, @Size и др)
    // BindingResult bindingResult - для хранения ошибок
    public ResponseEntity <HttpStatus> create(@RequestBody @Valid Cards cards,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            // обработка ошибок в случае их возникновения, отправит поле ошибки - и сообщение об ошибке
            StringBuilder errorMsg = new StringBuilder();
            List <FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()) // поле ошибки
                    .append(" - ")
                    .append(error.getDefaultMessage()) // сообщение из поля
                    .append("; ");
            }

            throw new CardNotCreatedException(errorMsg.toString());

        }
        cardsServices.save(cards);
        return ResponseEntity.ok(HttpStatus.OK); // отправляется пустой HTTP ответ со статусом 200 (сообщение ок)
    }


    @ExceptionHandler
    private ResponseEntity<CardErrorResponse> handleException (CardNotCreatedException e){
        CardErrorResponse response = new CardErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

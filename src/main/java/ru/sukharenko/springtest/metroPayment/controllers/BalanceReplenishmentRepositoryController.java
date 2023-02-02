package ru.sukharenko.springtest.metroPayment.controllers;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sukharenko.springtest.metroPayment.models.BalanceReplenishment;
import ru.sukharenko.springtest.metroPayment.models.Cards;
import ru.sukharenko.springtest.metroPayment.services.BalanceReplenishmentServices;
import ru.sukharenko.springtest.metroPayment.util.CardNotCreatedException;

import java.util.List;

@RestController
@RequestMapping("/br")
public class BalanceReplenishmentRepositoryController {

    private final BalanceReplenishmentServices bRS;

    public BalanceReplenishmentRepositoryController(BalanceReplenishmentServices bRS) {
        this.bRS = bRS;
    }
    @GetMapping("/hello") //http://localhost:8080/br/hello
    public String hello(){
        return "Hello from BalanceReplenishmentRepositoryController!";
    } // тестовый метод


    @GetMapping("/all") //http://localhost:8080/br/all
    public List<BalanceReplenishment> getBR(){
        return bRS.findAll();
    } // вернем список всех пополнений баланса

    @PostMapping("/new")//http://localhost:8080/br/new
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BalanceReplenishment bR,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            // обработка ошибок в случае их возникновения, отправит поле ошибки - и сообщение об ошибке
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()) // поле ошибки
                        .append(" - ")
                        .append(error.getDefaultMessage()) // сообщение из поля
                        .append("; ");
            }

            throw new CardNotCreatedException(errorMsg.toString());

        }

        bRS.save(bR);
        return ResponseEntity.ok(HttpStatus.OK); // отправляется пустой HTTP ответ со статусом 200 (сообщение ок)
    }



}


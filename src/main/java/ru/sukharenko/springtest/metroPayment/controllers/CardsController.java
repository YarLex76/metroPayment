package ru.sukharenko.springtest.metroPayment.controllers;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sukharenko.springtest.metroPayment.dto.CardsDTO;
import ru.sukharenko.springtest.metroPayment.models.Cards;
import ru.sukharenko.springtest.metroPayment.services.CardsServices;
import ru.sukharenko.springtest.metroPayment.util.CardErrorResponse;
import ru.sukharenko.springtest.metroPayment.util.CardNotCreatedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController   // @RestController ==  @Controller + @ResponseBody (отдает Json на get запрос)
@RequestMapping("/card")
public class CardsController {

    private final CardsServices cardsServices; // объявляем переменную типа CardsServices для использования его методов
    private final ModelMapper modelMapper;
    @Autowired
    public CardsController(CardsServices cardsServices, ModelMapper modelMapper) {
        this.cardsServices = cardsServices;
        this.modelMapper = modelMapper;
    } // конструктор для CardsServices и modelMapper

    @GetMapping("/hello") //http://localhost:8080/card/hello
    public String hello(){
        return "Hello from CardsController!";
    } // тестовый метод

    @GetMapping("/all") //http://localhost:8080/card/all
    public List<CardsDTO> getCards(){ // вернем список всех карт
        return cardsServices.findAll().stream().map(this::convertToCardDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}") //http://localhost:8080/card/1     // вернем одну карту id
    public CardsDTO getCard(@PathVariable("id") long id ){
        //Cards cards = new Cards(cardsServices.findOne(id));
        return convertToCardDTO(cardsServices.findOne(id));
    }

    @GetMapping("/email/{email}") //http://localhost:8080/card/email/test1@test.ru -- вернет баланс карты по мылу
    public int getEmail(@PathVariable("email") String email){ // вернем баланс карты по email
        CardsDTO cardsDTO = convertToCardDTO(cardsServices.findOne(email));
        return cardsDTO.getCardBalance();
    }

    @PostMapping ("/new")//http://localhost:8080/card/new
    // метод создания новой карты
    // @RequestBody  - преобразует json в объект класса Cards
    // @Valid - проверит входящие данные на соответствие аннотаций над полями в классе Cards (@NotEmpty, @Size и др)
    // BindingResult bindingResult - для хранения ошибок
    public ResponseEntity <HttpStatus> create(@RequestBody @Valid CardsDTO cardDTO,
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
        cardsServices.save(convertToCard(cardDTO)); // конвертирую cardDTO -> card и сохраняю card
        return ResponseEntity.ok(HttpStatus.OK); // отправляется пустой HTTP ответ со статусом 200 (сообщение ок)
    }


    @ExceptionHandler
    private ResponseEntity<CardErrorResponse> handleException (CardNotCreatedException e){
        CardErrorResponse response = new CardErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Cards convertToCard (CardsDTO cardDTO){
        // Cards card = new Cards();
        // card.setEmail(cardDTO.getEmail());
        // return card;
        return modelMapper.map(cardDTO,Cards.class); //конвертируем DTO в класс Cards
    }

    private CardsDTO convertToCardDTO (Cards card){
        return modelMapper.map(card, CardsDTO.class); // объект класса Cards конвертируем в DTO
    }

}

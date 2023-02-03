package ru.sukharenko.springtest.metroPayment.controllers;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sukharenko.springtest.metroPayment.dto.BalanceReplenishmentDTO;
import ru.sukharenko.springtest.metroPayment.dto.CardsDTO;
import ru.sukharenko.springtest.metroPayment.models.BalanceReplenishment;
import ru.sukharenko.springtest.metroPayment.models.Cards;
import ru.sukharenko.springtest.metroPayment.services.BalanceReplenishmentServices;
import ru.sukharenko.springtest.metroPayment.services.CardsServices;
import ru.sukharenko.springtest.metroPayment.util.CardNotCreatedException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/br")
public class BalanceReplenishmentController {

    private final BalanceReplenishmentServices bRS;

    private final CardsServices cardsServices;
    private final ModelMapper modelMapper;

    public BalanceReplenishmentController(BalanceReplenishmentServices bRS, CardsServices cardsServices, ModelMapper modelMapper) {
        this.bRS = bRS;
        this.cardsServices = cardsServices;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/hello") //http://localhost:8080/br/hello
    public String hello(){
        return "Hello from BalanceReplenishmentRepositoryController!";
    } // тестовый метод

    @GetMapping("/{id}") //http://localhost:8080/br/1     // вернем один платеж по id
    public BalanceReplenishmentDTO balanceReplenishmentDTO(@PathVariable("id") long id ){
        //Cards cards = new Cards(cardsServices.findOne(id));
        return convertToBalanceReplenishmentDTO(bRS.findOne(id));
    }

    @GetMapping("/all") //http://localhost:8080/br/all
    public List<BalanceReplenishmentDTO> getBR(){ // вернем список всех пополнений баланса
        return bRS.findAll().stream().map(this::convertToBalanceReplenishmentDTO).collect(Collectors.toList());
    }

    @GetMapping("/email/{email}") //http://localhost:8080/br/email/test@test.ru
    public List<BalanceReplenishmentDTO> getAllByEmail(@PathVariable("email") String email){
        // вернем список всех пополнений баланса по конкретному email
        Cards owner = cardsServices.findOne(email); // нахожу карту с такой почтой
        return bRS.findAllByEmail(owner).stream().map(this::convertToBalanceReplenishmentDTO).collect(Collectors.toList());
    }

    @PostMapping("/new")//http://localhost:8080/br/new
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BalanceReplenishmentDTO bRDTO,
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
        bRS.save(convertToBalanceReplenishment(bRDTO));
        topUpBalance(bRDTO);
        return ResponseEntity.ok(HttpStatus.OK); // отправляется пустой HTTP ответ со статусом 200 (сообщение ок)
    }

    private BalanceReplenishment convertToBalanceReplenishment (BalanceReplenishmentDTO balanceReplenishmentDTO){
        BalanceReplenishment balanceReplenishment = new BalanceReplenishment();
        balanceReplenishment = modelMapper.map(balanceReplenishmentDTO, BalanceReplenishment.class); // свожу имеющиеся данные
        String email = balanceReplenishmentDTO.getEmail(); // получаю почту из платежа
        Cards cards = cardsServices.findOne(email); // нахожу карту с такой почтой
        balanceReplenishment.setOwner(cards); // назначаю платежу владельца
        balanceReplenishment.setDateTrip(new Date()); // устанавливаю дату
        return balanceReplenishment; // возвращаю обновленный платеж
    }

    private BalanceReplenishmentDTO convertToBalanceReplenishmentDTO (BalanceReplenishment balanceReplenishment){
         BalanceReplenishmentDTO balanceReplenishmentDTO = modelMapper.map(balanceReplenishment, BalanceReplenishmentDTO.class);
         Cards cards = balanceReplenishment.getOwner(); // создаю новую карту
         String email = cards.getEmail(); // получаю почту этой карты
         balanceReplenishmentDTO.setEmail(email); // назначаю почту объекты DTO
         return balanceReplenishmentDTO; // возвращаю объект DTO

    }

    private void topUpBalance (BalanceReplenishmentDTO balanceReplenishmentDTO){ // обновление данных баланса
        String email = balanceReplenishmentDTO.getEmail(); // получаю почту из платежа
        Cards card = cardsServices.findOne(email); // нахожу карту с такой почтой
        card.setCardBalance(card.getCardBalance() + balanceReplenishmentDTO.getPriceTrip()); // обновляю баланс карты
        cardsServices.saveAndUpdate(card);
    }





}


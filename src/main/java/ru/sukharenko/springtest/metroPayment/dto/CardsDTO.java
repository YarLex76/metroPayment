package ru.sukharenko.springtest.metroPayment.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CardsDTO {

    @NotEmpty(message = "email should not be empty") // email не должен быть пустым
    @Size(min = 3, max = 30, message = "the email must be from 3 to 30 char ") //
    @Email(message = "sample email - username@mail.com")
    private String email;

    // Конструкторы почему-то не нужны????
    /*  public CardsDTO() {
    }

    public CardsDTO(String email) {
        this.email = email;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

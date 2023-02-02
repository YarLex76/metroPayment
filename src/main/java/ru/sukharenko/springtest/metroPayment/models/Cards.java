package ru.sukharenko.springtest.metroPayment.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;



@Entity // помечает класс связанный с БД
@Table(name = "cards") // соответствует таблице "cards"
public class Cards {

    @jakarta.persistence.Id
    @Column (name = "id") // соответствие колонке id
    @GeneratedValue (strategy = GenerationType.IDENTITY) // генерация первичного ключа на сервере
    private Long id;

    @Column (name = "email")
    @NotEmpty (message = "email should not be empty") // email не должен быть пустым
    @Size (min = 3, max = 30, message = "the email must be from 3 to 30 char ") //
    @Email(message = "sample email - username@mail.com")
    private String email;

    @Column (name = "card_balance")
    private int cardBalance;

    public Cards() { // пустой конструктор
    }

    public Cards(String email) {   // 2 конструктор, присваиваем только номер карты, id на стороне DB, баланс == 0
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(int cardBalance) {
        this.cardBalance = cardBalance;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", cardBalance=" + cardBalance +
                '}';
    }
}

package ru.sukharenko.springtest.metroPayment.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import ru.sukharenko.springtest.metroPayment.models.Cards;

import java.util.Date;

public class BalanceReplenishmentDTO {

    private String email; // владелец платежа email

    @Min(value = 1, message = "Price trip >= 1")
    private int priceTrip;

    @Column(name = "date_trip")
    @Temporal(TemporalType.TIMESTAMP) // для конвертации даты из java в дату DB
    private Date dateTrip;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPriceTrip() {
        return priceTrip;
    }

    public void setPriceTrip(int priceTrip) {
        this.priceTrip = priceTrip;
    }

    public Date getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(Date dateTrip) {
        this.dateTrip = dateTrip;
    }
}

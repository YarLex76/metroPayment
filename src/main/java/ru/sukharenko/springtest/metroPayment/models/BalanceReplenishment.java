package ru.sukharenko.springtest.metroPayment.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.util.Date;

//класс для пополнения баланса карты
@Entity // помечает класс связанный с БД
@Table(name = "balance_replenishment") // соответствует таблице "balance_replenishment"
public class BalanceReplenishment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерация первичного ключа на сервере
    private long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    //@JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private Cards owner; // владелец платежа

    @Column(name = "date_trip")
    @Temporal(TemporalType.TIMESTAMP) // для конвертации даты из java в дату DB
    private Date dateTrip;

    @Column(name = "price_trip")
    @Min(value = 1, message = "Price trip >= 1")
    private int priceTrip;

    public BalanceReplenishment() {
    }

    public BalanceReplenishment(Cards owner, int priceTrip) {
        this.owner = owner;
        this.priceTrip = priceTrip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cards getOwner() {
        return owner;
    }

    public void setOwner(Cards owner) {
        this.owner = owner;
    }

    public Date getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(Date dateTrip) {
        this.dateTrip = dateTrip;
    }

    public int getPriceTrip() {
        return priceTrip;
    }

    public void setPriceTrip(int priceTrip) {
        this.priceTrip = priceTrip;
    }

    @Override
    public String toString() {
        return "BalanceReplenishment{" +
                "id=" + id +
                ", owner=" + owner +
                ", dateTrip=" + dateTrip +
                ", priceTrip=" + priceTrip +
                '}';
    }
}





package ru.sukharenko.springtest.metroPayment.models;


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

    @ManyToOne
    @JoinColumn (name = "card_id", referencedColumnName = "id")
    private Cards card;

    @Column(name = "date_trip")
    @Temporal(TemporalType.TIMESTAMP) // для конвертации даты из java в дату DB
    private Date dateTrip;

    @Column(name = "price_trip")
    @Min(value = 1, message = "Price trip >= 1")
    private int priceTrip;

    public BalanceReplenishment() {
    }

    public BalanceReplenishment(Cards card, Date dateTrip) {
        this.card = card;
        this.dateTrip = dateTrip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
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
                ", card=" + card +
                ", dateTrip=" + dateTrip +
                ", priceTrip=" + priceTrip +
                '}';
    }
}





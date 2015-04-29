package cz.muni.fi.PB138.main.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * BarUTomaDataAnalyst
 */
public class Order {

    private LocalDate datetime;
    private long userID;
    private long barID;
    private BigDecimal price;

    public LocalDate getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getBarID() {
        return barID;
    }

    public void setBarID(long barID) {
        this.barID = barID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "datetime=" + datetime +
                ", userID=" + userID +
                ", barID=" + barID +
                ", price=" + price +
                '}';
    }

    public Order(LocalDate datetime, BigDecimal price, long barID, long userID) {
        this.datetime = datetime;
        this.price = price;
        this.barID = barID;
        this.userID = userID;
    }
}

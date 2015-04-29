package cz.muni.fi.PB138.main.entities;

import java.math.BigDecimal;

/**
 * BarUTomaDataAnalyst
 */
public class Drink {

    private String name;
    private BigDecimal price;
    private double alcohol;
    //private Map<long, double> ingredients; //long IdIngredient, double QuantityIngredient

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", alcohol=" + alcohol +
                '}';
    }

    public Drink(String name, BigDecimal price, double alcohol) {
        this.name = name;
        this.price = price;
        this.alcohol = alcohol;
    }
}

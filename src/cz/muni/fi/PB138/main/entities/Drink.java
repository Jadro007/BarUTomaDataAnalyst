package cz.muni.fi.PB138.main.entities;

import java.math.BigDecimal;

/**
 * BarUTomaDataAnalyst
 */
public class Drink {

    private String name;
    private BigDecimal price;
    private double alcoholQuantity;
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

    public double getAlcoholQuantity() {
        return alcoholQuantity;
    }

    public void setAlcoholQuantity(double alcoholQuantity) {
        this.alcoholQuantity = alcoholQuantity;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", alcoholQuantity=" + alcoholQuantity +
                '}';
    }

    public Drink(String name, BigDecimal price, double alcoholQuantity) {
        this.name = name;
        this.price = price;
        this.alcoholQuantity = alcoholQuantity;
    }
}

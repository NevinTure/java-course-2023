package edu.hw3.stock_market_problem;

import java.util.Objects;

public class Stock {
    private int price;
    //other data

    public Stock(int price) {
        setPrice(price);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be greater or equal to zero!");
        }
        this.price = price;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return price == stock.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}

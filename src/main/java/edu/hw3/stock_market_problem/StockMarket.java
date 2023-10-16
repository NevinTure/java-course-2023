package edu.hw3.stock_market_problem;

public interface StockMarket {
    /** Добавить акцию */
    void add(Stock stock);
    /** Удалить акцию */
    void remove(Stock stock);
    /** Самая дорогая акция */
    Stock mostValuableStock();
}

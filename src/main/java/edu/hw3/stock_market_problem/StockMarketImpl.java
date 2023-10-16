package edu.hw3.stock_market_problem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class StockMarketImpl implements StockMarket {

    private static final Comparator<Stock> DEFAILT_STOCK_COMPARATOR = Comparator.comparing(Stock::getPrice).reversed();
    private Comparator<Stock> stockComparator;
    private final PriorityQueue<Stock> storage;

    public StockMarketImpl() {
        storage = new PriorityQueue<>(DEFAILT_STOCK_COMPARATOR);
    }

    public StockMarketImpl(Comparator<Stock> stockComparator) {
        this.stockComparator = stockComparator;
        storage = new PriorityQueue<>(stockComparator);
    }

    @Override
    public void add(Stock stock) {
        storage.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        storage.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return storage.peek();
    }

    public void setStockComparator(Comparator<Stock> stockComparator) {
        this.stockComparator = stockComparator;
    }
}


package com.jpmorgan.sssm.controller;

import com.jpmorgan.sssm.catalog.Catalog;
import com.jpmorgan.sssm.model.Trade;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author franciscojosemartinezpaez
 */
public class PricerImpl implements Pricer {
    
    private Map<Long, Trade> trades = new TreeMap<>();
    
    /**
     * @param trades the trades to set
     */
    public void setTrades(Map<Long, Trade> trades) {
        this.trades = trades;
    }
    
    @Override
    public Map<Long, Trade> getTrades() {
        return trades;
    }

    @Override
    public void saveTrade(Trade trade) {
        
        trades.put(trade.getSendingTime().getTime(), trade);
    }

    @Override
    public Double calculateDividendYield(Catalog.Stock stock, Double price) {
        
        Double dividendYield = null;
        
        if (price > 0.0) {
        
            switch(stock.getType()) {

                case Common:
                    dividendYield = stock.getLastDividend() / price;
                    break;

                case Preferred:
                    dividendYield = (stock.getFixedDividend() * stock.getParValue()) / price;
                    break;

                default:
                    break;
            }
        }
        else {
            throw new ArithmeticException("Price must be greater than zero");
        }
        
        return dividendYield;
    }

    @Override
    public Double calculatePERatio(Catalog.Stock stock, Double price) {
        
        Double peRatio = null;
        
        if (price > 0.0) {
            if (stock.getLastDividend() > 0.0) {
                peRatio = price / stock.getLastDividend();
            }
            else {
                throw new ArithmeticException("Last Dividend must be greater than zero");
            }
        }
        else {
            throw new ArithmeticException("Price must be greater than zero");
        }
            
        return peRatio;
    }

    @Override
    public Double calculateVWSP(Long periodTime) {
        
        Double sumPrice_x_Quantity = 0.0;
        Integer quantity = 0;
        Long startPeriod = new Date().getTime() - periodTime;       
        SortedMap<Long, Trade> tradesInPeriod = ((TreeMap<Long, Trade>) getTrades()).tailMap(startPeriod);

        for (Trade trade: tradesInPeriod.values()) {
            
            quantity += trade.getQuantityShares();
            sumPrice_x_Quantity += trade.getPrice() * trade.getQuantityShares();
        }
        
        Double res = sumPrice_x_Quantity / quantity;
                
        return res;
    }

    @Override
    public Double calculateGeometricMean(TreeSet<Catalog.Stock> stocks) {
        
        Double size = stocks.size() * 1.0;
        Double mul = recursiveMultiplication(stocks);
        Double res = Math.pow(mul, 1.0/size);
        
        return res;
    }

    private Double recursiveMultiplication(TreeSet<Catalog.Stock> stocks) {
        
        Double res = 1.0;
        
        if (!stocks.isEmpty()) {
            
            Catalog.Stock stock = stocks.first();
            Integer val = stock.getParValue();
            stocks.remove(stock);
            res *= val * recursiveMultiplication(stocks);
        }
        
        return res;
    }
    
}

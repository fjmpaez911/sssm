
package com.jpmorgan.sssm.controller;

import com.jpmorgan.sssm.catalog.Catalog;
import com.jpmorgan.sssm.model.Trade;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author franciscojosemartinezpaez
 */
public interface Pricer {
    
    /**
     * Record a trade request
     * 
     * @param trade 
     */
    void saveTrade(Trade trade);
    
    /**
     * Get the recorded trades
     * 
     * @return the recorded trades
     */
    Map<Long, Trade> getTrades();
    
    /**
     * Calculate the Dividend Yield
     * 
     * @param stock
     * @param price
     * @return the Dividend Yield
     */
    Double calculateDividendYield(Catalog.Stock stock, Double price);
    
    /**
     * Calculate the P/E Ratio
     * 
     * @param stock
     * @param price
     * @return the P/E Ratio
     */
    Double calculatePERatio(Catalog.Stock stock, Double price);
    
    /**
     * Calculate Volume Weighted Stock Price
     * 
     * @param periodTime period of time in milis
     * @return the Volume Weighted Stock Price
     */
    Double calculateVWSP(Long periodTime);
    
    /**
     * Calculate the Geometric Mean
     * 
     * @param stocks list of stocks
     * @return the Geometric Mean
     */
    Double calculateGeometricMean(TreeSet<Catalog.Stock> stocks);
}

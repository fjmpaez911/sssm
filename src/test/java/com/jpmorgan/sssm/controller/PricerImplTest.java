
package com.jpmorgan.sssm.controller;

import com.jpmorgan.sssm.catalog.Catalog;
import com.jpmorgan.sssm.model.Trade;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author franciscojosemartinezpaez
 */
public class PricerImplTest {
    
    static final long ONE_MINUTE_IN_MILLIS = 60000;
    
    Map<Long, Trade> trades = null;
    PricerImpl instance = null;

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        trades = new TreeMap<>();
        instance = new PricerImpl();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetTrades() {
        
        Date time = new Date();
        Trade trade = new Trade(0, Catalog.Stock.TEA, Catalog.SideEnum.SELL, 150.7, 100);
        trades.put(time.getTime(), trade);
        instance.setTrades(trades);
        assertEquals(trades, instance.getTrades());

    }

    @Test
    public void testGetTrades() {

        Date time = new Date();
        Trade trade = new Trade(0, Catalog.Stock.TEA, Catalog.SideEnum.SELL, 150.7, 100, time);
        instance.saveTrade(trade);
        assertEquals(trade, instance.getTrades().get(time.getTime()));

    }

    @Test
    public void testSaveTrade() {

        Date time = new Date();
        Trade trade = new Trade(0, Catalog.Stock.TEA, Catalog.SideEnum.SELL, 150.7, 100, time);
        instance.saveTrade(trade);
        assertEquals(trade, instance.getTrades().get(time.getTime()));

    }

    @Test (expected = ArithmeticException.class)
    public void testCalculateDividendYield() {

        Catalog.Stock stock = Catalog.Stock.ALE;
        Double price = 0.0;
        instance.calculateDividendYield(stock, price);

    }
    
    @Test
    public void testCalculateDividendYield_2() {

        Catalog.Stock stock = Catalog.Stock.TEA;
        Double price = 150.0;
        Double expResult = 0.0;
        Double result = instance.calculateDividendYield(stock, price);
        assertEquals(expResult, result);

    }
    
    @Test
    public void testCalculateDividendYield_3() {

        Catalog.Stock stock = Catalog.Stock.POP;
        Double price = 150.0;
        Double expResult = stock.getLastDividend() / price;
        Double result = instance.calculateDividendYield(stock, price);
        assertEquals(expResult, result);

    }
    
    @Test
    public void testCalculateDividendYield_4() {

        Catalog.Stock stock = Catalog.Stock.GIN;
        Double price = 150.0;
        Double expResult = (stock.getParValue() * stock.getFixedDividend()) / price;
        Double result = instance.calculateDividendYield(stock, price);
        assertEquals(expResult, result);

    }

    @Test
    public void testCalculatePERatio() {

        Catalog.Stock stock = Catalog.Stock.POP;
        Double price = 150.0;
        Double expResult = price / stock.getLastDividend();
        Double result = instance.calculatePERatio(stock, price);
        assertEquals(expResult, result);

    }
    
    @Test (expected = ArithmeticException.class)
    public void testCalculatePERatio_2() {

        Catalog.Stock stock = Catalog.Stock.TEA;
        Double price = 150.0;
        instance.calculatePERatio(stock, price);

    }
    
    @Test (expected = ArithmeticException.class)
    public void testCalculatePERatio_3() {

        Catalog.Stock stock = Catalog.Stock.POP;
        Double price = 0.0;
        instance.calculatePERatio(stock, price);

    }

    @Test
    public void testCalculateVWSP() {

        Date time = new Date();
        Trade trade = new Trade(2, Catalog.Stock.TEA, Catalog.SideEnum.SELL, 150.7, 100, time);
        instance.saveTrade(trade);

        
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date before10Min = new Date(t - (10 * ONE_MINUTE_IN_MILLIS));
        
        trade = new Trade(1, Catalog.Stock.POP, Catalog.SideEnum.BUY, 234.6, 100, before10Min);
        instance.saveTrade(trade);
        
        
        date = Calendar.getInstance();
        t = date.getTimeInMillis();
        Date before20Min = new Date(t - (20 * ONE_MINUTE_IN_MILLIS));
        
        trade = new Trade(0, Catalog.Stock.GIN, Catalog.SideEnum.BUY, 24.6, 250, before20Min);
        instance.saveTrade(trade);
        
        
        Long periodTime = (15 * ONE_MINUTE_IN_MILLIS);

        Double expResult = 192.65;
        Double result = instance.calculateVWSP(periodTime);
        assertEquals(expResult, result);

    }

    @Test
    public void testCalculateGeometricMean() {

        DecimalFormat df = new DecimalFormat("#.##");
        
        TreeSet<Catalog.Stock> stocks = new TreeSet<>();
        stocks.addAll(Arrays.asList(Catalog.Stock.values()));
        Double expResult = 108.45;
        Double result = instance.calculateGeometricMean(stocks);
        assertEquals(expResult.toString(), df.format(result).replaceAll(",", "."));

    }
    
}

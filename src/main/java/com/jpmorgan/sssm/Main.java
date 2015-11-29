
package com.jpmorgan.sssm;

import com.jpmorgan.sssm.catalog.Catalog;
import com.jpmorgan.sssm.controller.Pricer;
import com.jpmorgan.sssm.controller.PricerImpl;
import com.jpmorgan.sssm.exception.EnumNotFoundException;
import com.jpmorgan.sssm.model.Trade;
import com.jpmorgan.sssm.utils.DataUtils;
import com.jpmorgan.sssm.utils.EnumUtils;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franciscojosemartinezpaez
 */
public class Main {
    
    private static final int DELAY_TO_RECORD = 100;
    
    private static DecimalFormat df = new DecimalFormat("#.##"); 
    private static Pricer pricer = new PricerImpl();
    private static Scanner scan = new Scanner (System.in);
    
    private static void checkParameters(String[] args) {
        
        if (args.length == 2 && "generate".equals(args[0])) {

            int nTrades = Integer.parseInt(args[1]);

            generateTestFile (nTrades);
            
            System.exit (0);
        }
    }
    
    private static void generateTestFile(int nTrades) {
        
        try {

            Catalog.Stock[] stocksV = Catalog.Stock.values();
            Integer rdnStock = Integer.parseInt(Long.toString(Math.round(Math.random() * (stocksV.length - 1))), 10);
            Double rdnPrice = Math.random() * 1000;
                
            System.out.println(stocksV[rdnStock]);
            System.out.println(df.format(rdnPrice));

            System.out.println(15);
                
            generateTrades(nTrades);
        }
        catch (InterruptedException | NumberFormatException ex) {
        }
    }
    
    private static void generateTrades(int nTrades) throws InterruptedException {
        
        System.out.println(nTrades);
        
        while (nTrades-- > 0) {
            
            Catalog.SideEnum[] sides = Catalog.SideEnum.values();
            Catalog.Stock[] stocks = Catalog.Stock.values();
            Integer rdnSide = Integer.parseInt(Long.toString(Math.round(Math.random() * (sides.length - 1))), 10);
            Integer rdnStock = Integer.parseInt(Long.toString(Math.round(Math.random() * (stocks.length - 1))), 10);
            Double rdnPrice = Math.random() * 1000;
            Integer rdnQuantity = Integer.parseInt(Long.toString(Math.round(Math.random() * 1000)), 10);

            System.out.println(stocks[rdnStock] + " " + sides[rdnSide] + " " + df.format(rdnPrice) + " " + rdnQuantity);
        }
    }
    
    private static String scanInput() {
        
        String str = DataUtils.EMPTY_STRING;
        
        try {
            str = scan.next();
        } 
        catch (Exception e) {
            Logger.getLogger(Main.class.getName()).info("Ocurred an error scaning the input");
        }
        
        return str;
    }
    
    private static Integer scanInputInteger() {
        
        Integer n = 0;
        
        try {
            n = scan.nextInt();
        } 
        catch (Exception e) {
            Logger.getLogger(Main.class.getName()).info("Ocurred an error scaning the input");
        }
        
        return n;
    }
    
    private static Catalog.Stock inputStock() {
        
        Catalog.Stock stockSelected = null;
        boolean correctStock;
        do {
            correctStock = true;
            System.out.println("You must type one of these Stocks: ");

            for (Catalog.Stock stock : Catalog.Stock.values()) {
                System.out.println("    " + stock.name());
            }

            System.out.print("Enter a stock: ");
            String str = scanInput();

            try {
                stockSelected = EnumUtils.lookupEnumByConstantName(Catalog.Stock.class, str);
            } catch (EnumNotFoundException ex) {
                System.out.print("Please select a correct stock: ");
                correctStock = false;
            }
        } while (!correctStock);
       
        return stockSelected;
    }
    
    private static Double inputPrice() {
        
        Double value = null;
        boolean correctPrice;
        do {
            correctPrice = true;
            System.out.print("Enter a price: ");
            String str = scanInput();
            str = str.replaceAll(",", ".");

            try {
                value = Double.parseDouble(str);
            }
            catch (NumberFormatException e) {
                System.out.println("Enter a price in a correct format [100 or 80,5 or 79.3]");
                correctPrice = false;
            }
            
        } while (!correctPrice);
       
        return value;
    }

    private static int inputTimePeriod() {
        
        System.out.print("Enter a period time in minutes to calculate Volume Weighted Stock Price based on trades in past (X) minutes: ");
        return scanInputInteger();
    }
    
    private static void inputTrade(int id) {
        
        String str;
        
        try {
            
            str = scanInput();
            Catalog.Stock stockOfTrade = EnumUtils.lookupEnumByConstantName(Catalog.Stock.class, str);

            str = scanInput();
            Catalog.SideEnum sideOfTrade = EnumUtils.lookupEnumByConstantName(Catalog.SideEnum.class, str);

            str = scanInput();
            str = DataUtils.isNumeric(str) ? str.replaceAll(",", ".") : null;
            Double priceOfTrade = Double.parseDouble(str);

            int quantityOfTrade = scanInputInteger();

            Trade trade = new Trade(id, stockOfTrade, sideOfTrade, priceOfTrade, quantityOfTrade);
            pricer.saveTrade(trade);
        } 
        catch (EnumNotFoundException | NumberFormatException | NullPointerException ex) {
            Logger.getLogger(Main.class.getName()).info("The trade is not in a correct format. Discard this trade.");
        }
    }
    
    private static void inputTrades() {
        
        System.out.println("You must type a number of trades to record: ");
        
        int numberOfTrades = scanInputInteger();

        System.out.println("Enter the trade's detail in this format (STOCK SIDE PRICE QUANTITY) ");
        System.out.println("ex: POP SELL 120,4 100 ");

        int cont = 0;

        while (numberOfTrades > cont) {

            inputTrade (cont++);

            waitToRecord (DELAY_TO_RECORD);   
        }
    }

    private static void calculateDividendYield(Catalog.Stock stock, Double price) {
       
        try {
            System.out.println("Dividend Yield: " + df.format(pricer.calculateDividendYield(stock, price)));
        }
        catch (ArithmeticException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Ocurred an error calculating Dividend Yield", ex);
        }
    }
    
    private static void calculatePERatio(Catalog.Stock stock, Double price) {
       
        try {
            System.out.println("P/E Ratio: " + df.format(pricer.calculatePERatio(stock, price)));
        }
        catch (ArithmeticException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Ocurred an error calculating P/E Ratio", ex);
        }
    }
    
    private static void calculateVWSP(int period) {
        
        if (!pricer.getTrades().isEmpty()) {

            try {
                Long periodInMilis = new Long(period * 60 * 1000);
                System.out.println("Volume Weighted Stock Price: " + df.format(pricer.calculateVWSP(periodInMilis)));
            }
            catch (Exception e) {
                Logger.getLogger(Main.class.getName()).info("Error calculating Volume Weighted Stock Price");
            }
        }
    }

    private static void calculateGeometricMean() {
        
        TreeSet<Catalog.Stock> stocks = new TreeSet<>();
        stocks.addAll(Arrays.asList(Catalog.Stock.values()));
        
        System.out.println("Geometric Mean: " + df.format(pricer.calculateGeometricMean(stocks)));
    }

    /**
     * This method simulate a delay
     * 
     * @param delay in milis
     */
    private static void waitToRecord(int delay) {
        
        try {
            Thread.sleep(Integer.parseInt(Long.toString(Math.round(Math.random() * delay + 1)), 10));
        } 
        catch (InterruptedException ex) {
        }
    }
    
    private static void freeResources() {
        
        scan.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        checkParameters(args);
        
        Catalog.Stock stock = inputStock();
        Double price = inputPrice();
        calculateDividendYield(stock, price);
        calculatePERatio(stock, price);

        int period = inputTimePeriod();
        inputTrades();
        calculateVWSP(period);
        
        calculateGeometricMean();
        
        freeResources();
        
    }
    
}

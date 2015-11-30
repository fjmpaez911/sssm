
package com.jpmorgan.sssm.model;

import com.jpmorgan.sssm.catalog.Catalog;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author franciscojosemartinezpaez
 */
public class Trade implements Serializable{
    
    private int id;
    private Catalog.Stock stock;
    private Date sendingTime;
    private int quantityShares;
    private Catalog.SideEnum side;
    private Double price;

    /**
     * Constructor
     */
    public Trade () {
        
    }
    
    /**
     * Constructor
     * 
     * @param id
     * @param stock
     * @param side
     * @param price
     * @param quantityShares 
     */
    public Trade (int id, Catalog.Stock stock, Catalog.SideEnum side, Double price, int quantityShares) {
        
        this.id = id;
        this.stock = stock;
        this.side = side;
        this.price = price;
        this.quantityShares = quantityShares;
        this.sendingTime = new Date();
    }
    
    /**
     * Constructor
     * 
     * @param id
     * @param stock
     * @param side
     * @param price
     * @param quantityShares 
     * @param sendingTime 
     */
    public Trade (int id, Catalog.Stock stock, Catalog.SideEnum side, Double price, int quantityShares, Date sendingTime) {
        
        this.id = id;
        this.stock = stock;
        this.side = side;
        this.price = price;
        this.quantityShares = quantityShares;
        this.sendingTime = sendingTime;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the stock
     */
    public Catalog.Stock getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Catalog.Stock stock) {
        this.stock = stock;
    }

    /**
     * @return the sendingTime
     */
    public Date getSendingTime() {
        return sendingTime;
    }

    /**
     * @param sendingTime the date to set
     */
    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    /**
     * @return the quantityShares
     */
    public int getQuantityShares() {
        return quantityShares;
    }

    /**
     * @param quantityShares the quantityShares to set
     */
    public void setQuantityShares(int quantityShares) {
        this.quantityShares = quantityShares;
    }

    /**
     * @return the side
     */
    public Catalog.SideEnum getSide() {
        return side;
    }

    /**
     * @param side the side to set
     */
    public void setSide(Catalog.SideEnum side) {
        this.side = side;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(45, 77)
                .append(stock)
                .append(quantityShares)
                .append(side)
                .append(price).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Trade)) {
            return false;
        }
        final Trade other = (Trade) obj;
        return new EqualsBuilder()
                .append(stock, other.stock)
                .append(quantityShares, other.quantityShares)
                .append(side, other.side)
                .append(price, other.price).isEquals();
    }
    
}

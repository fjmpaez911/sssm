
package com.jpmorgan.sssm.catalog;

import java.io.Serializable;

/**
 *
 * @author franciscojosemartinezpaez
 */
public class Catalog {
    
    /**
     * Stock.
     */
    public static enum Stock implements Serializable {
    
        TEA(TypeEnum.Common, 0, null, 100), POP(TypeEnum.Common, 8, null, 100), ALE(TypeEnum.Common, 23, null, 60), GIN(TypeEnum.Preferred, 8, 0.02, 100), JOE(TypeEnum.Common, 13, null, 250);

        private final TypeEnum type;
        private final Integer lastDividend;
        private final Double fixedDividend;
        private final Integer parValue;

        /**
         * Constructor
         * 
         * @param type
         * @param lastDividend
         * @param fixedDividend
         * @param parValue 
         */
        private Stock(TypeEnum type, Integer lastDividend, Double fixedDividend, Integer parValue)
        {
            this.type = type;
            this.lastDividend = lastDividend;
            this.fixedDividend = fixedDividend;
            this.parValue = parValue;
        }

        /**
         * @return the type of stock
         */
        public TypeEnum getType() {
            return type;
        }

        /**
         * @return the lastDividend
         */
        public Integer getLastDividend() {
            return lastDividend;
        }

        /**
         * @return the fixedDividend
         */
        public Double getFixedDividend() {
            return fixedDividend;
        }

        /**
         * @return the parValue
         */
        public Integer getParValue() {
            return parValue;
        }

    }
    
    /**
     * Type of stock.
     */
    public static enum TypeEnum implements Serializable {

        Common, Preferred;

        /**
         * Constructor
         */
        private TypeEnum() {
        }

    }

    /**
     * Side of trade.
     */
    public static enum SideEnum implements Serializable {

        BUY, SELL;

        /**
         * Constructor
         */
        private SideEnum() {
        }

    }
    
}

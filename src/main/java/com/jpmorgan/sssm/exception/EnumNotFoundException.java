
package com.jpmorgan.sssm.exception;

/**
 * Exception thrown when a Enumerate does not exist when trying valueOf
 *
 * @author franciscojosemartinezpaez
 */
public class EnumNotFoundException extends Exception {

    public Class enumClass;
    public String nameNotFound;

    /**
     * Constructor.
     *
     * @param message exception descriptive message
     * @param enumClass enum class
     * @param nameNotFound name not found
     * @param cause original cause
     */
    public EnumNotFoundException(String message, Class enumClass, String nameNotFound, Throwable cause) {
        super(message, cause);
        this.enumClass = enumClass;
        this.nameNotFound = nameNotFound;
    }

    /**
     * Get the enum class
     *
     * @return enumClass enumClass
     */
    public Class getEnumClass() {
        return enumClass;
    }

    /**
     * Get the name not found
     *
     * @return nameNotFound nameNotFound
     */
    public String getNameNotFound() {
        return nameNotFound;
    }
}


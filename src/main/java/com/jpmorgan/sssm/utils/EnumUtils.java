
package com.jpmorgan.sssm.utils;

import com.jpmorgan.sssm.exception.EnumNotFoundException;

/**
 * Utility Class for Enumerates
 *
 * @author franciscojosemartinezpaez
 * @param <T> Generic Class of the enumerate to be analyzed
 * @param <K> Entity Object
 */
public class EnumUtils<T, K> {

    /**
     * Lookup an enumerate by constant's name.
     *
     * Method that should be used to lookup an enumerate instead of using its valueof method. In
     * order to avoid a runtime exception, its use will be a good practice.
     *
     * @param <T> Generic Class of the enumerate to be analyzed
     * @param enumType Class of the enumerate to be analyzed
     * @param enumConstantName name of the constant's property in base to proceed
     * @return the corresponding enumerate that match with the enumConstantName provided
     * @throws EnumNotFoundException enum not found exception
     */
    public static <T extends Enum<T>> T lookupEnumByConstantName(Class<T> enumType, String enumConstantName)
            throws EnumNotFoundException {
        try {
            T result = Enum.valueOf(enumType, enumConstantName);
            return result;
        } catch (IllegalArgumentException ex) {
            throw new EnumNotFoundException("Enum not found", enumType, enumConstantName, ex);
        }
    }
    
}

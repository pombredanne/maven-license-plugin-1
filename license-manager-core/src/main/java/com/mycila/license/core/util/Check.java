package com.mycila.license.core.util;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Check {

    private Check() {}

    public static void notNull(Object o, String param) {
        if(o == null) {
            throw new IllegalArgumentException(param + " cannot be null");
        }
    }

}

package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class Check {

    private Check() {}

    static void notNull(Object o, String param) {
        if(o == null) {
            throw new LicenseManagerException(param + " cannot be null");
        }
    }

}

package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class LicenseManagerException extends RuntimeException {
    public LicenseManagerException(String message) {
        super(message);
    }

    public LicenseManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}

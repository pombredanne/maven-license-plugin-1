package com.mycila.license.core;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class LicenseHeaderTest {

    @Test
    public void test_create() throws Exception {
        try {
            LicenseHeader.newLicenseHeader(new Settings(), getClass().getResource("inexisting"));
        } catch (Exception e) {
            assertEquals(e.getMessage(),  "Header location cannot be null");
        }
        LicenseHeader header = LicenseHeader.newLicenseHeader(new Settings(), getClass().getResource("/com/mycila/license/core/header1.txt"));
        assertEquals(header.getHeaderLocation(), getClass().getResource("/com/mycila/license/core/header1.txt"));
    }

    @Test
    public void test_placeholders() throws Exception {
        try {
            LicenseHeader.newLicenseHeader(new Settings(), getClass().getResource("inexisting"));
        } catch (Exception e) {
            assertEquals(e.getMessage(),  "Header location cannot be null");
        }
        LicenseHeader header = LicenseHeader.newLicenseHeader(new Settings(), getClass().getResource("/com/mycila/license/core/header1.txt"));
        header
                .replace("company").by("Mycila")
                .replace("year").by("2009");
        
    }
}

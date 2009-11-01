package com.mycila.license.core;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class LicenseHeaderTest {

    @Test
    public void test_create() throws Exception {
        try {
            new LicenseHeaderImpl(new SettingsImpl(), getClass().getResource("inexisting"));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Header location cannot be null");
        }

        LicenseHeaderImpl header = new LicenseHeaderImpl(new SettingsImpl(), getClass().getResource("/com/mycila/license/core/header1.txt"));
        assertEquals(header.getHeaderLocation(), getClass().getResource("/com/mycila/license/core/header1.txt"));

        URLClassLoader cl = new URLClassLoader(new URL[]{new File("src/test/resources/com/mycila/license/core/header1.txt.jar").toURI().toURL()});
        header = new LicenseHeaderImpl(new SettingsImpl(), cl.getResource("header1.txt"));
        assertEquals(header.getHeaderLocation().toString(), "jar:file:/home/kha/workspace/perso/license-manager/license-manager-core/src/test/resources/com/mycila/license/core/header1.txt.jar!/header1.txt");

        new LicenseHeaderImpl(new SettingsImpl()., getClass().getResource("/com/mycila/license/core/header1.txt"));
    }

    @Test
    public void test_placeholders() throws Exception {
        try {
            new LicenseHeaderImpl(new SettingsImpl(), getClass().getResource("inexisting"));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Header location cannot be null");
        }
        LicenseHeaderImpl header = new LicenseHeaderImpl(new SettingsImpl(), getClass().getResource("/com/mycila/license/core/header1.txt"));
        header
                .replace("company").by("Mycila")
                .replace("year").by("2009");
    }
}

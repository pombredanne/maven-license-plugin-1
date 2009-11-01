package com.mycila.license.core;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentTypeTest {

    @SuppressWarnings({"ObjectEqualsNull"})
    @Test
    public void test_create() throws Exception {
        HeaderStyles headerStyles = new HeaderStyles();
        headerStyles.loadDefaults();

        DocumentTypeImpl type1 = new DocumentTypeImpl("xml");
        DocumentTypeImpl type2 = new DocumentTypeImpl("xml");
        type2.headerStyle = headerStyles.getByName("xml");
        DocumentTypeImpl type3 = new DocumentTypeImpl("java");
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());
        assertTrue(type1.equals(type1));
        assertFalse(type1.equals(type3));
        assertFalse(type1.equals(null));
        assertFalse(type1.equals(new Object()));
        assertFalse(type1.hashCode() == type3.hashCode());
        assertEquals(type1.compareTo(type2), 0);
        assertTrue(type1.compareTo(type3) > 0);
    }

}
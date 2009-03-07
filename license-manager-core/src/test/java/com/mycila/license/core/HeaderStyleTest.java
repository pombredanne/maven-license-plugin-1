package com.mycila.license.core;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class HeaderStyleTest {

    @SuppressWarnings({"ObjectEqualsNull"})
    @Test
    public void test_create() throws Exception {
        HeaderStyle style1 = new HeaderStyle("xml");
        HeaderStyle style2 = new HeaderStyle("xml");
        style2.description = "hello";
        HeaderStyle style3 = new HeaderStyle("javadoc");
        assertEquals(style1, style2);
        assertEquals(style1.hashCode(), style2.hashCode());
        assertTrue(style1.equals(style1));
        assertFalse(style1.equals(style3));
        assertFalse(style1.equals(null));
        assertFalse(style1.equals(new Object()));
        assertFalse(style1.hashCode() == style3.hashCode());
        assertEquals(style1.compareTo(style2), 0);
        assertTrue(style1.compareTo(style3) > 0);
    }

}

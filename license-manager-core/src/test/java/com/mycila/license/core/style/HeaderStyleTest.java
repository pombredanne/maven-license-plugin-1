package com.mycila.license.core.style;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class HeaderStyleTest {

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
        System.out.println(style1);
        System.out.println(style2);
    }

    @Test
    public void test_get_style() throws Exception {
        try {
            new HeaderStyles().getHeaderStyle("inexisting");
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Inexisting Header Style: inexisting");
        }
    }

    @Test
    public void test_create_style() throws Exception {
        try {
            new HeaderStyles().add("a")
                    .defineStartLine("    ")
                    .defineEnding("EOL-->")
                    .detectBegining("(\\s|\\t)*<!--.*$")
                    .detectEnding("(\\s|\\t)*<!--.*$")
                    .build();
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Begining definition cannot be null");
        }
        try {
            new HeaderStyles().add("a")
                    .defineBegining("<!--EOL")
                    .defineEnding("EOL-->")
                    .detectBegining("(\\s|\\t)*<!--.*$")
                    .detectEnding("(\\s|\\t)*<!--.*$")
                    .build();
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "StartLine definition cannot be null");
        }
        try {
            new HeaderStyles().add("a")
                    .defineBegining("<!--EOL")
                    .defineStartLine("    ")
                    .detectBegining("(\\s|\\t)*<!--.*$")
                    .detectEnding("(\\s|\\t)*<!--.*$")
                    .build();
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Ending definition cannot be null");
        }
        try {
            new HeaderStyles().add("a")
                    .defineBegining("<!--EOL")
                    .defineStartLine("    ")
                    .defineEnding("EOL-->")
                    .detectEnding("(\\s|\\t)*<!--.*$")
                    .build();
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Begining detection cannot be null");
        }
        try {
            new HeaderStyles().add("a")
                    .defineBegining("<!--EOL")
                    .defineStartLine("    ")
                    .defineEnding("EOL-->")
                    .detectBegining("(\\s|\\t)*<!--.*$")
                    .build();
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Ending detection cannot be null");
        }
    }

    @Test
    public void test_add_style() throws Exception {
        HeaderStyles styles = new HeaderStyles();
        assertEquals(styles.size(), 0);
        HeaderStyle style = styles.add("xml")
                .description("XML header style")
                .defineBegining("<!--EOL")
                .defineStartLine("    ")
                .defineEnding("EOL-->")
                .detectSkip("^<\\?xml.*>$")
                .detectBegining("(\\s|\\t)*<!--.*$")
                .detectEnding("(\\s|\\t)*<!--.*$")
                .build();
        assertEquals(styles.size(), 1);
        assertEquals(style.toString(), "HeaderStyle 'xml' (XML header style)\n" +
                " - begining: <!--EOL\n" +
                " - before each line:     \n" +
                " - ending: EOL-->");
        assertEquals(style.detectSkip, "^<\\?xml.*>$");
        assertEquals(style.detectBegining, "(\\s|\\t)*<!--.*$");
        assertEquals(style.detectEnding, "(\\s|\\t)*<!--.*$");
    }

    @Test
    public void test_add_duplicate_style() throws Exception {
        HeaderStyles styles = new HeaderStyles();
        HeaderStyle style1 = styles.add("xml")
                .description("XML header style")
                .defineBegining("<!--EOL")
                .defineStartLine("    ")
                .defineEnding("EOL-->")
                .detectSkip("^<\\?xml.*>$")
                .detectBegining("(\\s|\\t)*<!--.*$")
                .detectEnding("(\\s|\\t)*<!--.*$")
                .build();
        assertEquals(styles.getHeaderStyle("XML"), style1);
        HeaderStyle style2 = styles.add("XML")
                .description("XML header style")
                .defineBegining("<!--EOL")
                .defineStartLine("    ")
                .defineEnding("EOL-->")
                .detectSkip("^<\\?xml.*>$")
                .detectBegining("(\\s|\\t)*<!--.*$")
                .detectEnding("(\\s|\\t)*<!--.*$")
                .build();
        assertEquals(styles.getHeaderStyle("xml"), style2);
    }

}

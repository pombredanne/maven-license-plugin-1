package com.mycila.license.core.header;

import static com.mycila.license.core.header.HeaderStyles.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class HeaderStylesTest {

    @Test
    public void test_load_defaults() throws Exception {
        HeaderStyles headerStyles = newHeaderStyles();
        headerStyles.loadDefaults();
        assertEquals(headerStyles.size(), 23);
        for (HeaderStyle style : headerStyles.getHeaderStyles()) {
            System.out.println(style);
        }
    }

    @Test
    public void test_get_style() throws Exception {
        try {
            newHeaderStyles().getByName("inexisting");
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Inexisting Header Style: inexisting");
        }
    }

    @Test
    public void test_add_loc() throws Exception {
        assertEquals(newHeaderStyles().add(getClass().getResource("/com/mycila/license/core/header/styles.xml")).size(), 1);
        assertEquals(newHeaderStyles().add(getClass().getResource("/com/mycila/license/core/header/styles-min.xml")).size(), 1);
        try {
            newHeaderStyles().add(getClass().getResource("/com/mycila/license/core/header/styles-invalid.xml"));
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Style definition at"));
        }
    }

    @Test
    public void test_create_style() throws Exception {
        try {
            newHeaderStyles().add("a")
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
            newHeaderStyles().add("a")
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
            newHeaderStyles().add("a")
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
            newHeaderStyles().add("a")
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
            newHeaderStyles().add("a")
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
        HeaderStyles styles = newHeaderStyles();
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
                " - ending: EOL-->\n" +
                " - detection to skip with: ^<\\?xml.*>$\n" +
                " - detection of begining with: (\\s|\\t)*<!--.*$\n" +
                " - detection of end with: (\\s|\\t)*<!--.*$");
        assertEquals(style.detectSkip, "^<\\?xml.*>$");
        assertEquals(style.detectBegining, "(\\s|\\t)*<!--.*$");
        assertEquals(style.detectEnding, "(\\s|\\t)*<!--.*$");
    }

    @Test
    public void test_add_duplicate_style() throws Exception {
        HeaderStyles styles = newHeaderStyles();
        HeaderStyle style1 = styles.add("xml")
                .description("XML header style")
                .defineBegining("<!--EOL")
                .defineStartLine("    ")
                .defineEnding("EOL-->")
                .detectSkip("^<\\?xml.*>$")
                .detectBegining("(\\s|\\t)*<!--.*$")
                .detectEnding("(\\s|\\t)*<!--.*$")
                .build();
        assertEquals(styles.getByName("XML"), style1);
        HeaderStyle style2 = styles.add("XML")
                .description("XML header style")
                .defineBegining("<!--EOL")
                .defineStartLine("    ")
                .defineEnding("EOL-->")
                .detectSkip("^<\\?xml.*>$")
                .detectBegining("(\\s|\\t)*<!--.*$")
                .detectEnding("(\\s|\\t)*<!--.*$")
                .build();
        assertEquals(styles.getByName("xml"), style2);
    }

}
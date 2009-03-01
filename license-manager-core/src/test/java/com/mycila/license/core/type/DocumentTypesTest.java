package com.mycila.license.core.type;

import com.mycila.license.core.style.HeaderStyles;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentTypesTest {

    HeaderStyles headerStyles;

    @BeforeClass
    public void setup() {
        headerStyles = new HeaderStyles();
        headerStyles.loadDefaults();
    }

    @Test
    public void test_load_defaults() throws Exception {
        DocumentTypes documentTypes = new DocumentTypes(headerStyles);
        documentTypes.loadDefaults();
        assertEquals(documentTypes.size(), 50);
        for (DocumentType documentType : documentTypes.getDocumentTypes()) {
            System.out.println(documentType);
        }
    }

    @Test
    public void test_get_type() throws Exception {
        try {
            new DocumentTypes(headerStyles).getByExtension("inexisting");
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Unsupported document type: inexisting");
        }
    }

    @Test
    public void test_add_mapping_loc() throws Exception {
        assertEquals(new DocumentTypes(headerStyles).add(getClass().getResource("/com/mycila/license/core/type/types.xml")).size(), 2);
        try {
            new DocumentTypes(headerStyles).add(getClass().getResource("/com/mycila/license/core/type/types-invalid.xml"));
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Mapping definition at"));
        }
        try {
            new DocumentTypes(headerStyles).add(getClass().getResource("/com/mycila/license/core/type/types-inexisting-style.xml"));
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Inexisting Header Style: inexisting"));
        }
    }

    @Test
    public void test_create_invalid_mapping() throws Exception {
        try {
            new DocumentTypes(headerStyles).map(null);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Document extension cannot be null");
        }
        try {
            new DocumentTypes(headerStyles).map("aa").to(null);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Header style cannot be null");
        }
    }


    @Test
    public void test_isSupported() throws Exception {
        DocumentTypes mappings = new DocumentTypes(headerStyles);
        mappings.loadDefaults();
        assertTrue(mappings.isExtensionSupported("java"));
        assertFalse(mappings.isExtensionSupported(null));
        assertFalse(mappings.isExtensionSupported(""));
        assertFalse(mappings.isExtensionSupported("inexisting"));
    }

    @Test
    public void test_create_mapping() throws Exception {
        DocumentTypes mappings = new DocumentTypes(headerStyles);
        assertEquals(mappings.size(), 0);
        DocumentType doc = mappings.map("java").to(headerStyles.getByName("javadoc"));
        assertEquals(mappings.size(), 1);
        assertEquals(doc.toString(), "DocumentType: java => HeaderStyle 'javadoc' (Javadoc header style)\n" +
                " - begining: /**\n" +
                " - before each line:  * \n" +
                " - ending:  */\n" +
                " - detection to skip with: null\n" +
                " - detection of begining with: (\\s|\\t)*/\\*.*$\n" +
                " - detection of end with: .*\\*/(\\s|\\t)*$");
    }

    @Test
    public void test_add_duplicate_mapping() throws Exception {
        DocumentTypes mappings = new DocumentTypes(headerStyles);
        DocumentType doc1 = mappings.map("java").to(headerStyles.getByName("javadoc"));
        assertEquals(mappings.getByExtension("java"), doc1);
        DocumentType doc2 = mappings.map("java").to(headerStyles.getByName("javadoc"));
        assertEquals(mappings.getByExtension("java"), doc2);
    }

}
package com.mycila.license.core;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class FileSelectorTest {

    @Test
    public void test_create_selection_with_def_excl() throws Exception {
        FileSelectionImpl selection = new FileSelector()
                .changeBaseDirectory(new File("src/test/resources"))
                .withDefaultExcludes()
                .include("**/*.xml")
                .exclude("**/types*")
                .select();
        assertEquals(selection.getBaseDirectory(), new File("src/test/resources"));
        assertTrue(selection.isUseDefaultExcludes());
        assertEquals(selection.getIncludes().size(), 1);
        assertEquals(selection.getExcludes().size(), 1);
        assertEquals(selection.getSelectedFile().size(), 3);
    }

    @Test
    public void test_select_include() throws Exception {
        FileSelectionImpl selection = new FileSelector()
                .changeBaseDirectory(new File("src/test/resources"))
                .withoutDefaultExcludes()
                .include("**/*.xml")
                .select();
        assertEquals(selection.getBaseDirectory(), new File("src/test/resources"));
        assertFalse(selection.isUseDefaultExcludes());
        assertEquals(selection.getIncludes().size(), 1);
        assertEquals(selection.getExcludes().size(), 0);
        assertEquals(selection.getSelectedFile().size(), 6);
    }

    @Test
    public void test_select_include_one_exclude_all() throws Exception {
        FileSelectionImpl selection = new FileSelector()
                .changeBaseDirectory(new File("src/test/resources"))
                .withoutDefaultExcludes()
                .include("**/*.xml")
                .exclude("**")
                .select();
        assertEquals(selection.getBaseDirectory(), new File("src/test/resources"));
        assertFalse(selection.isUseDefaultExcludes());
        assertEquals(selection.getIncludes().size(), 1);
        assertEquals(selection.getExcludes().size(), 1);
        assertEquals(selection.getSelectedFile().size(), 0);
    }

    @Test
    public void test_implicit_include_all() throws Exception {
        FileSelectionImpl selection = new FileSelector()
                .changeBaseDirectory(new File("src/test/resources"))
                .withDefaultExcludes()
                .exclude("**/types*")
                .select();
        assertEquals(selection.getBaseDirectory(), new File("src/test/resources"));
        assertTrue(selection.isUseDefaultExcludes());
        assertEquals(selection.getIncludes().size(), 0);
        assertEquals(selection.getExcludes().size(), 1);
        assertEquals(selection.getSelectedFile().size(), 4);
    }
}

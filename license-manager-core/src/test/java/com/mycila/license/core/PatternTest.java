package com.mycila.license.core;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class PatternTest {
    @Test
    public void test_patterns() throws Exception {
        String text = "/**\n" +
                " * Copyright (C) 2008 http://code.google.com/p/maven-license-plugin/\n" +
                " *\n" +
                " * Licensed under the Apache License, Version 2.0 (the \"License\");";
        assertFalse(text.matches(".*copyright.*"));
        assertFalse(text.matches(".*Copyright.*"));
        assertTrue(text.matches("(?s).*Copyright.*"));
        assertFalse(text.matches("(?s).*copyright.*"));
        assertTrue(text.matches("(?is).*copyright.*"));
    }
}

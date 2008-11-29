/**
 * Copyright (C) 2008 http://code.google.com/p/maven-license-plugin/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.mojo.license.util;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class SelectionTest {
    @Test
    public void test_default_select_all() {
        Selection selection = new Selection(new File("."), new String[0], new String[0], false);
        assertEquals(selection.getExcluded().length, 0);
        assertEquals(selection.getIncluded().length, 1);
        assertTrue(selection.getSelectedFiles().length > 0);
    }

    @Test
    public void test_limit_inclusion() {
        Selection selection = new Selection(new File("."), new String[]{"toto"}, new String[]{"tata"}, false);
        assertEquals(selection.getExcluded().length, 1);
        assertEquals(selection.getIncluded().length, 1);
        assertEquals(selection.getSelectedFiles().length, 0);
    }

    @Test
    public void test_limit_inclusion_and_check_default_excludes() {
        Selection selection = new Selection(new File("."), new String[]{"toto"}, new String[0], true);
        assertEquals(selection.getExcluded().length, 37); // default exludes from Scanner and Selection + toto
        assertEquals(selection.getIncluded().length, 1);
        assertEquals(selection.getSelectedFiles().length, 0);
        assertTrue(Arrays.asList(selection.getExcluded()).containsAll(Arrays.asList(Selection.OTHER_DEFAULT_EXCLUDES)));
    }
}

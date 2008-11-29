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

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class FileUtilsTest {
    @Test
    public void test_read_first_lines() throws Exception {
        String s = FileUtils.readFirstLines(new File("src/test/data/compileCP/test2.txt"), 3);
        assertTrue(s.contains("c"));
        assertFalse(s.contains("d"));
    }

    @Test
    public void test_locking() throws Exception {
        File file = new File("src/test/data/compileCP/test.txt");
        FileUtils.lock(file);
        try {
            FileUtils.lock(file);
            fail("Should not be able to acquire a lock");
        } catch (Exception e) {
        }
        FileUtils.unlock(file);
        try {
            FileUtils.lock(file);
            FileUtils.unlock(file);
        } catch (Exception e) {
            fail("Should be able to acquire a lock");
        }
    }
}

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

package com.google.code.mojo.license;

import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class QuietMojoTest {

    @Test
    public void test_load_header_from_relative_file() throws Exception {
        LicenseCheckMojo check = new LicenseCheckMojo();
        check.basedir = new File("src/test/resources/check");
        check.header = "header.txt";
        check.project = new MavenProjectStub();
        check.failIfMissing = false;

        MockedLog logger = new MockedLog();
        check.setLog(new DefaultLog(logger));

        check.execute();
        assertTrue(logger.getLogEntries() > 2);

        logger.clear();

        check.quiet = true;
        check.execute();
        assertEquals(logger.getLogEntries(), 2);
    }
}
/**
 * Copyright (C) 2008 Mathieu Carbou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.mojo.license.test;

import com.google.code.mojo.license.LicenseCheckMojo;
import com.google.code.mojo.license.util.Selection;
import com.google.code.mojo.license.util.resource.InvalidResourceException;
import org.apache.maven.plugin.MojoFailureException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class LicenseCheckMojoTest
{
    @Test(expectedExceptions = MojoFailureException.class)
    public void test_execution() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File(".");
                super.header = "src/etc/header.txt";
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test
    public void test_execution_quiet() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File(".");
                super.header = "src/etc/header.txt";
                super.quiet = true;
                super.failIfMissing = false;
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test
    public void test_execution_no_mapping() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File(".");
                super.header = "src/etc/header.txt";
                super.useDefaultMapping = false;
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test(expectedExceptions = InvalidResourceException.class)
    public void test_execution_bad_header() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File(".");
                super.header = "src/etc/header_bad.txt";
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test(expectedExceptions = MojoFailureException.class)
    public void test_execution_url_file_system() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("src/test/project");
                super.header = "documents/header.txt";
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }
    
    @Test(expectedExceptions = MojoFailureException.class)
    public void test_execution_url_classpath() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File(".");
                super.header = "header.txt"; // locate in test/resources
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test
    public void test_selection() throws Exception
    {
        Selection sel = Selection.newSelection(new File("."), null, null, true);
        assertEquals(sel.getBasedir(), new File("."));
        assertEquals(sel.getExcluded(), new String[] {"**/*~", "**/#*#", "**/.#*", "**/%*%", "**/._*", "**/CVS", "**/CVS/**", "**/.cvsignore", "**/SCCS", "**/SCCS/**", "**/vssver.scc", "**/.svn", "**/.svn/**", "**/.arch-ids", "**/.arch-ids/**", "**/.bzr", "**/.bzr/**", "**/.MySCMServerInfo", "**/.DS_Store", "target/**", "test-output/**", "release.properties",  "pom.xml", "cobertura.ser", ".clover/**", ".classpath", ".project", ".settings/**", "*.iml", "*.ipr", "*.iws"});
        assertEquals(sel.getIncluded(), new String[] {"**"});
    }

    @Test
    public void test_selection_2() throws Exception
    {
        Selection sel = Selection.newSelection(new File("src/test/project"), new String[] {"*"}, new String[] {"documents/**", "target/*", ".svn/*"}, false);
        assertEquals(sel.getBasedir(), new File("src/test/project"));
        assertEquals(sel.getExcluded(), new String[] {"documents/**", "target/*", ".svn/*"});
        assertEquals(sel.getIncluded(), new String[] {"*"});

        assertEquals(sel.getSelectedFiles(), new String[] {"pom.xml"});
        assertEquals(sel.getSelectedFiles(), new String[] {"pom.xml"});
    }

}

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
import com.google.code.mojo.license.LicenseFormatMojo;
import com.google.code.mojo.license.LicenseRemoveMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class LicenseRemoveMojoTest
{
    @BeforeTest
    public void copyFiles() throws Exception
    {
        FileUtils.copyDirectory(new File("src/test/project/documents"), new File("target/documents1"));
    }

    @Test
    public void remove_missing_header() throws Exception
    {
        LicenseRemoveMojo mojo = new LicenseRemoveMojo()
        {
            {
                super.includes = new String[] {"**/Java*.java"};
                super.basedir = new File("target/documents1");
                super.header = "target/documents1/header.txt";
                super.excludes = new String[] {"*.svn-base", "*header.txt"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
                super.mapping = new HashMap<String, String>()
                {
                    {
                        put("toto", "xml");
                    }
                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "remove_missing_header", expectedExceptions = MojoFailureException.class)
    public void check_before_add() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("target/documents1");
                super.header = "target/documents1/header.txt";
                super.excludes = new String[] {"*.svn-base", "*header.txt"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
                super.mapping = new HashMap<String, String>()
                {
                    {
                        put("toto", "xml");
                    }
                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "check_before_add")
    public void add_header() throws Exception
    {
        LicenseFormatMojo mojo = new LicenseFormatMojo()
        {
            {
                super.basedir = new File("target/documents1");
                super.header = "target/documents1/header.txt";
                super.excludes = new String[] {"*.svn-base", "*header.txt"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
                super.mapping = new HashMap<String, String>()
                {
                    {
                        put("toto", "xml");
                    }
                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "add_header")
    public void check_after_add() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("target/documents1");
                super.header = "target/documents1/header.txt";
                super.excludes = new String[] {"*.svn-base", "*header.txt"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
                super.mapping = new HashMap<String, String>()
                {
                    {
                        put("toto", "xml");
                    }
                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "check_after_add")
    public void remove_header() throws Exception
    {
        LicenseRemoveMojo mojo = new LicenseRemoveMojo()
        {
            {
                super.basedir = new File("target/documents1");
                super.header = "target/documents1/header.txt";
                super.excludes = new String[] {"*.svn-base", "*header.txt"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
                super.mapping = new HashMap<String, String>()
                {
                    {
                        put("toto", "xml");
                    }
                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "remove_header", expectedExceptions = MojoFailureException.class)
    public void check_after_remove_header() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("target/documents1");
                super.header = "target/documents1/header.txt";
                super.excludes = new String[] {"*.svn-base", "*header.txt"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2010");
                        put("email", "mathieu.carbou@gmail.com");
                    }

                };
                super.mapping = new HashMap<String, String>()
                {
                    {
                        put("toto", "xml");
                    }
                };
            }
        };
        mojo.execute();
    }

}
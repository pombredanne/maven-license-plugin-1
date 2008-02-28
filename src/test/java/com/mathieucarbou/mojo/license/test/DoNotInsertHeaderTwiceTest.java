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

package com.mathieucarbou.mojo.license.test;

import com.mathieucarbou.mojo.license.LicenseCheckMojo;
import com.mathieucarbou.mojo.license.LicenseFormatMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.FileUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class DoNotInsertHeaderTwiceTest
{
    @BeforeSuite
    public void copyFiles() throws Exception
    {
        FileUtils.copyDirectory(new File("src/test/project/documents"), new File("target/documents"));
    }

    @Test(expectedExceptions = MojoFailureException.class)
    public void test_check_before() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("target/documents");
                super.header = "target/documents/other-header.txt";
                super.includes = new String[] {"**/MockIP.java"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "test@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "test_check_before")
    public void test_add() throws Exception
    {
        LicenseFormatMojo mojo = new LicenseFormatMojo()
        {
            {
                super.basedir = new File("target/documents");
                super.header = "target/documents/other-header.txt";
                super.includes = new String[] {"**/MockIP.java"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "test@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "test_add")
    public void test_check_after() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("target/documents");
                super.header = "target/documents/other-header.txt";
                super.includes = new String[] {"**/MockIP.java"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2008");
                        put("email", "test@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "test_check_after", expectedExceptions = MojoFailureException.class)
    public void test_check_before_update() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("target/documents");
                super.header = "target/documents/other-header.txt";
                super.includes = new String[] {"**/MockIP.java"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2010");
                        put("email", "test@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "test_check_before_update")
    public void test_update() throws Exception
    {
        LicenseFormatMojo mojo = new LicenseFormatMojo()
        {
            {
                super.basedir = new File("target/documents");
                super.header = "target/documents/other-header.txt";
                super.includes = new String[] {"**/MockIP.java"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2010");
                        put("email", "test@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }

    @Test(dependsOnMethods = "test_update")
    public void test_check_after_update() throws Exception
    {
        LicenseCheckMojo mojo = new LicenseCheckMojo()
        {
            {
                super.basedir = new File("target/documents");
                super.header = "target/documents/other-header.txt";
                super.includes = new String[] {"**/MockIP.java"};
                super.properties = new HashMap<String, String>()
                {
                    {
                        put("year", "2010");
                        put("email", "test@gmail.com");
                    }

                };
            }
        };
        mojo.execute();
    }
}
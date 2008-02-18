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

package com.mathieucarbou.mojo.license.document;

import static com.mathieucarbou.mojo.license.util.FileUtils.*;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

/**
 * <b>Date:</b> 15-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Header
{
    private final File headerFile;
    private final String headerContent;
    private final String headerContentOneLine;
    private final int lineCount;

    private Header(File headerFile)
    {
        this.headerFile = headerFile;
        try
        {
            // read header
            this.headerContent = read(headerFile);

            // get line count
            int pos = 0;
            int line = 1;
            while((pos = headerContent.indexOf("\n", pos + 1)) != -1)
            {
                line++;
            }
            lineCount = line;

            // get header on one line
            headerContentOneLine = remove(headerContent, " ", "\t", "\r", "\n");
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Cannot read header document " + headerFile, e);
        }
    }

    public String asString()
    {
        return headerContent;
    }

    public String asOneLineString()
    {
        return headerContentOneLine;
    }

    public int getLineCount()
    {
        return lineCount;
    }

    public File getFile()
    {
        return headerFile;
    }
    
    @Override
    public String toString()
    {
        return asString();
    }

    public static Header headerFromFile(File header) throws MojoExecutionException
    {
        return new Header(header);
    }

}

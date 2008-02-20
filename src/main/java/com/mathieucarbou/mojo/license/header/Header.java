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

package com.mathieucarbou.mojo.license.header;

import static com.mathieucarbou.mojo.license.util.FileUtils.*;

import java.io.File;
import java.util.Map;

/**
 * <b>Date:</b> 15-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Header
{
    private final File headerFile;
    private final String headerContent;
    private final String headerContentOneLine;
    private String[] lines;

    private Header(File headerFile, Map<String, String> properties)
    {
        this.headerFile = headerFile;
        try
        {
            this.headerContent = read(headerFile, properties);
            lines = headerContent.split("\n");
            headerContentOneLine = remove(headerContent, " ", "\t", "\r", "\n");
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Cannot read header document " + headerFile + ". Cause: " + e.getMessage(), e);
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
        return lines.length;
    }

    public File getFile()
    {
        return headerFile;
    }

    public String buildForType(HeaderType type)
    {
        StringBuilder newHeader = new StringBuilder();
        if(notEmpty(type.getFirstLine()))
        {
            newHeader.append(type.getFirstLine()).append("\n");
        }
        for(String line : getLines())
        {
            newHeader.append(type.getBeforeEachLine()).append(line).append("\n");
        }
        if(notEmpty(type.getEndLine()))
        {
            newHeader.append(type.getEndLine()).append("\n");
        }
        return newHeader.toString();
    }

    @Override
    public String toString()
    {
        return asString();
    }

    public static Header headerFromFile(File header, Map<String, String> properties)
    {
        return new Header(header, properties);
    }

    public String[] getLines()
    {
        return lines;
    }

    private boolean notEmpty(String str)
    {
        return str != null && str.length() > 0;
    }
}

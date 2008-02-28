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

import java.net.URL;
import java.util.Map;

/**
 * <b>Date:</b> 15-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Header
{
    private final URL location;
    private final String headerContent;
    private final String headerContentOneLine;
    private String[] lines;

    private Header(URL location, Map<String, String> properties)
    {
        this.location = location;
        try
        {
            this.headerContent = read(location, properties);
            lines = headerContent.split("\n");
            headerContentOneLine = remove(headerContent, " ", "\t", "\r", "\n");
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Cannot read header document " + location + ". Cause: " + e.getMessage(), e);
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

    public URL getLocation()
    {
        return location;
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
        return newHeader.append("\n").toString();
    }

    @Override
    public String toString()
    {
        return asString();
    }

    public String[] getLines()
    {
        return lines;
    }

    private boolean notEmpty(String str)
    {
        return str != null && str.length() > 0;
    }

    public static Header readFrom(URL location, Map<String, String> properties)
    {
        return new Header(location, properties);
    }
    
}

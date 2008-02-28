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

import java.util.regex.Pattern;

/**
 * <b>Date:</b> 17-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public enum HeaderType
{
    ////////// COMMENT TYPES //////////

    JAVA("/**", " * ", " */"),
    XML("<!--\n", "    ", "\n-->", "^<\\?xml.*>$"),
    APT("~~", "~~ ", "~~"),
    PROPERTIES("#", "# ", "#", "^#!.*$"),
    TEXT("====", "    ", "===="),
    BATCH("@REM", "@REM ", "@REM"),
    SQL("--", "-- ", "--"),
    JSP("<%--\n", "    ", "\n--%>"),
    UNKNOWN("", "", "");

    ////////////////////////////////////

    private final String firstLine;
    private final String beforeEachLine;
    private final String endLine;
    private final Pattern skipLinePattern;

    private HeaderType(String firstLine, String beforeEachLine, String endLine)
    {
        this(firstLine, beforeEachLine, endLine, null);
    }

    private HeaderType(String firstLine, String beforeEachLine, String endLine, String skipLine)
    {
        this.firstLine = firstLine;
        this.beforeEachLine = beforeEachLine;
        this.endLine = endLine;
        this.skipLinePattern = skipLine == null ? null : Pattern.compile(skipLine);
    }

    public String getFirstLine()
    {
        return firstLine;
    }

    public String getBeforeEachLine()
    {
        return beforeEachLine;
    }

    public String getEndLine()
    {
        return endLine;
    }

    public boolean mustSkip(String line)
    {
        return skipLinePattern != null && line != null && skipLinePattern.matcher(line).matches();
    }

    public static HeaderType fromName(String name)
    {
        for(HeaderType type : values())
        {
            if(type.name().equalsIgnoreCase(name))
            {
                return type;
            }
        }
        return UNKNOWN;
    }

}

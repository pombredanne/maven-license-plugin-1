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

/**
 * <b>Date:</b> 17-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public enum CommentType
{
    ////////// COMMENT TYPES //////////

    JAVA("/**", " * ", " */"),
    XML("<!--", "    ", "-->"),
    APT("", "~~ ", ""),
    PROPERTIES("", "# ", ""),
    TEXT("", "    ", ""),
    BATCH("", "REM ", ""),
    SQL("", "-- ", ""),
    UNKNOWN("", "", "");

    ////////////////////////////////////

    private final String firstLine;
    private final String eachLine;
    private final String endLine;

    private CommentType(String firstLine, String eachLine, String endLine)
    {
        this.firstLine = firstLine;
        this.eachLine = eachLine;
        this.endLine = endLine;
    }

    public String getFirstLine()
    {
        return firstLine;
    }

    public String getEachLine()
    {
        return eachLine;
    }

    public String getEndLine()
    {
        return endLine;
    }

    public static CommentType fromName(String name)
    {
        for(CommentType type : values())
        {
            if(type.name().equalsIgnoreCase(name))
            {
                return type;
            }
        }
        return UNKNOWN;
    }
}

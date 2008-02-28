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

import com.mathieucarbou.mojo.license.util.FileContent;

/**
 * <b>Date:</b> 23-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class HeaderParser
{
    private final int beginPosition;
    private final int endPosition;
    private final boolean existingHeader;

    private String line;

    public HeaderParser(FileContent fileContent, HeaderType headerType)
    {
        beginPosition = findBeginPosition(fileContent, headerType);
        existingHeader = hasHeader(fileContent, headerType);
        this.endPosition = existingHeader ? findEndPosition(fileContent) : -1;
    }

    public int getBeginPosition()
    {
        return beginPosition;
    }

    public int getEndPosition()
    {
        return endPosition;
    }

    public boolean gotHeader()
    {
        return existingHeader;
    }

    private int findBeginPosition(FileContent fileContent, HeaderType headerType)
    {
        int beginPosition = 0;
        line = fileContent.nextLine();
        if(line != null && headerType.mustSkip(line))
        {
            beginPosition = fileContent.getPosition();
            line = fileContent.nextLine();
        }
        return beginPosition;
    }

    private boolean hasHeader(FileContent fileContent, HeaderType headerType)
    {
        // skip blank lines
        while(line != null && "".equals(line.trim()))
        {
            line = fileContent.nextLine();
        }
        
        // check if there is already a header
        boolean gotHeader = false;
        if(line != null && line.indexOf(headerType.getFirstLine().replaceAll("\n", "")) != -1)
        {
            StringBuilder existingHeader = new StringBuilder();
            do
            {
                existingHeader.append(line.toLowerCase());
                line = fileContent.nextLine();
            }
            while(line != null && ("".equals(line) || line.indexOf(headerType.getEndLine().replace("\n", "")) == -1 || line.startsWith(headerType.getBeforeEachLine())));
            gotHeader = existingHeader.indexOf("copyright") != -1;
            existingHeader.setLength(0);
            existingHeader = null;
        }
        return gotHeader;
    }

    private int findEndPosition(FileContent fileContent)
    {
        // we check if there is a header, if the next line is the blank line of the header
        int end = fileContent.getPosition();
        line = fileContent.nextLine();
        if(line != null && "".equals(line.trim()))
        {
            end = fileContent.getPosition();
        }
        return end;
    }

    public static HeaderParser parseHeader(FileContent fileContent, HeaderType headerType)
    {
        return new HeaderParser(fileContent, headerType);
    }
}

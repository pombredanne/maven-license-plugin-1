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

package com.mathieucarbou.mojo.license.document.impl;

import com.mathieucarbou.mojo.license.document.AbstractDocument;
import com.mathieucarbou.mojo.license.document.Header;
import static com.mathieucarbou.mojo.license.util.FileUtils.*;

import java.io.IOException;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class JavaDocument extends AbstractDocument
{
    private static final String[] toRemove = {" ", "\t", "\r", "\n", "/**", "*/", "*"};

    public JavaDocument(String file)
    {
        super(file);
    }

    public boolean hasHeader(Header header)
    {
        try
        {
            String fileHeader = readFirstLines(getFile(), header.getLineCount() + 10);
            String fileHeaderOneLine = remove(fileHeader, toRemove);
            return fileHeaderOneLine.contains(header.asOneLineString());
        }
        catch(IOException e)
        {
            throw new IllegalStateException("Cannot read file " + getFile(), e);
        }
    }

    public void setHeader(Header header)
    {
    }

    public static String extension()
    {
        return "java";
    }
}

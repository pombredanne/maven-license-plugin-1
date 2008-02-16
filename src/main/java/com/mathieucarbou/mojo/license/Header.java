/**
 * Copyright (C) 2008 Mathieu Carbou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this document except in compliance with the License.
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

package com.mathieucarbou.mojo.license;

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

    private Header(File headerFile) throws MojoExecutionException
    {
        this.headerFile = headerFile;
        try
        {
            this.headerContent = read(headerFile);
        }
        catch(Exception e)
        {
            throw new MojoExecutionException("Cannot read header document " + headerFile, e);
        }
    }

    public String asString()
    {
        return headerContent;
    }

    public File asFile()
    {
        return headerFile;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Header header = (Header) o;

        if(headerFile != null ? !headerFile.equals(header.headerFile) : header.headerFile != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return (headerFile != null ? headerFile.hashCode() : 0);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Header");
        sb.append("{headerContent='").append(headerContent).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static Header headerFromFile(File header) throws MojoExecutionException
    {
        return new Header(header);
    }

}

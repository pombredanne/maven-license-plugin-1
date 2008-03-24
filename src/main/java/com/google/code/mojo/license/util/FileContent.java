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

package com.google.code.mojo.license.util;

import static com.google.code.mojo.license.util.FileUtils.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <b>Date:</b> 21-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class FileContent
{
    private final File file;
    private final StringBuilder fileContent;

    private int position;

    private FileContent(File file)
    {
        try
        {
            this.file = file;
            this.fileContent = new StringBuilder(read(file));
        }
        catch(IOException e)
        {
            throw new IllegalArgumentException("Unable to read file " + file + ". Cause: " + e.getMessage(), e);
        }
    }

    public void write()
    {
        OutputStream out = lock(file);
        try
        {
            out.write(fileContent.toString().getBytes());
        }
        catch(Exception e)
        {
        }
        finally
        {
            closeSilently(out);
            unlock(file);
        }
    }

    public boolean endReached()
    {
        return position >= fileContent.length();
    }

    public String nextLine()
    {
        if(endReached())
        {
            return null;
        }
        int lf = fileContent.indexOf("\n", position);
        int eol = lf == -1 || lf == 0 ? fileContent.length() : fileContent.charAt(lf - 1) == '\r' ? lf - 1 : lf;
        String str = fileContent.substring(position, eol);
        position = lf == -1 ? fileContent.length() : lf + 1;
        return str;
    }

    public int getPosition()
    {
        return position;
    }

    public void delete(int start, int end)
    {
        fileContent.delete(start, end);
    }

    public void insert(int index, String str)
    {
        fileContent.insert(index, str);
        // remove possible double blank lines caused when a blank line was previously there (the header add a blank line)
        position = index + str.length();
        String next = nextLine();
        if(next != null && "".equals(next))
        {
            fileContent.delete(index + str.length(), position);
        }
    }

    public static FileContent readFrom(File file)
    {
        return new FileContent(file);
    }

    @Override
    public String toString()
    {
        return file.toString();
    }
}

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

package com.mathieucarbou.mojo.license.util;

import org.codehaus.plexus.util.IOUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class FileUtils
{
    public static String read(File file) throws IOException
    {
        Reader reader = new BufferedReader(new FileReader(file));
        String content = IOUtil.toString(reader);
        reader.close();
        return content;
    }

    public static String read(String file) throws IOException
    {
        return read(new File(file));
    }

    public static String readFirstLines(String file, int lineCount) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while(lineCount > 0 && (line = reader.readLine()) != null)
        {
            lineCount--;
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String remove(String str, String... chars)
    {
        for(String s : chars)
        {
            str = str.replace(s, "");
        }
        return str;
    }
}

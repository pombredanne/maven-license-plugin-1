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

import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.InterpolationFilterReader;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class FileUtils
{
    private static final Map<File, FileLock> locks = new HashMap<File, FileLock>();

    static
    {
        Runtime.getRuntime().addShutdownHook(new Thread("File lock cleaner")
        {
            @Override
            public void run()
            {
                for(File file : new HashSet<File>(locks.keySet()))
                {
                    unlock(file);
                }
            }
        });
    }

    public static String eol()
    {
        return System.getProperty("line.separator");
    }

    public static String read(URL location, Map<String, String> properties) throws IOException
    {
        Reader reader = new InterpolationFilterReader(new BufferedReader(new InputStreamReader(location.openStream())), properties);
        String content = IOUtil.toString(reader);
        reader.close();
        return content;
    }

    public static String read(File file, String encoding) throws IOException
    {
        Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        String content = IOUtil.toString(reader);
        reader.close();
        return content;
    }

    public static String readFirstLines(File file, int lineCount) throws IOException
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

    public static FileOutputStream lock(File file)
    {
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            FileLock lock = out.getChannel().tryLock();
            if(lock == null)
            {
                throw new IllegalStateException("Cannot acquire a lock on file " + file + ". Please verify that this file is not used by another process.");
            }
            locks.put(file, lock);
            return out;
        }
        catch(IOException e)
        {
            throw new IllegalStateException("An I/O error occured when trying to get a lock on file " + file + ". Please verify that this file is not used by another process. Cause: " + e.getMessage(), e);
        }
    }

    public static void unlock(File file)
    {
        FileLock lock = locks.get(file);
        if(lock != null)
        {
            try
            {
                lock.release();
            }
            catch(IOException e)
            {
            }
            locks.remove(file);
        }
    }

    public static void closeSilently(Closeable closeable)
    {
        if(closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch(Exception e)
            {
            }
        }
    }
}

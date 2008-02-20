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

import static com.mathieucarbou.mojo.license.document.DocumentType.*;
import com.mathieucarbou.mojo.license.header.Header;
import com.mathieucarbou.mojo.license.header.HeaderType;
import static com.mathieucarbou.mojo.license.util.FileUtils.*;
import static org.codehaus.plexus.util.FileUtils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Document
{
    private final File file;
    private final DocumentType documentType;
    private final HeaderType headerType;

    private Document(File file, HeaderType headerType)
    {
        this.file = file;
        this.documentType = fromExtension(extension(file.getName()));
        this.headerType = headerType;
    }

    public File getFile()
    {
        return file;
    }

    public DocumentType getDocumentType()
    {
        return documentType;
    }

    public boolean isSupported()
    {
        return headerType != HeaderType.UNKNOWN;
    }

    public boolean hasHeader(Header header)
    {
        try
        {
            String fileHeader = readFirstLines(file, header.getLineCount() + 10);
            String fileHeaderOneLine = remove(fileHeader, headerType.getFirstLine().trim(), headerType.getEndLine().trim(), headerType.getBeforeEachLine().trim(), "\n", "\r", "\t", " ");
            return fileHeaderOneLine.contains(header.asOneLineString());
        }
        catch(IOException e)
        {
            throw new IllegalStateException("Cannot read file " + getFile(), e);
        }
    }

    public static Document newDocument(File file, HeaderType headerType)
    {
        return new Document(file, headerType);
    }

    public void updateHeader(Header header)
    {
        String newHeader = header.buildForType(headerType);

        try
        {
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            FileLock lock = lock(raf);

            

            lock.release();
        }
        catch(FileNotFoundException e)
        {
            throw new IllegalStateException("The following file has been selected but is not available anymore: " + file);
        }
        catch(IOException e)
        {
            throw new IllegalStateException("An I/O error occured when updating header file " + file, e);
        }

        System.out.println(newHeader);

        // TODO: update
    }

    private FileLock lock(RandomAccessFile raf)
    {
        try
        {
            FileLock lock = raf.getChannel().tryLock();
            if(lock == null)
            {
                throw new IllegalStateException("Cannot acquire a lock on file " + file + ". Please verify that this file is not used by another process.");
            }
            return lock;
        }
        catch(IOException e)
        {
            throw new IllegalStateException("An I/O error occured when trying to get a lock on file " + file + ". Please verify that this file is not used by another process.", e);
        }
    }

}

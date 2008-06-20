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

package com.google.code.mojo.license.document;

import static com.google.code.mojo.license.document.DocumentType.*;
import com.google.code.mojo.license.header.Header;
import com.google.code.mojo.license.header.HeaderParser;
import static com.google.code.mojo.license.header.HeaderParser.*;
import com.google.code.mojo.license.header.HeaderType;
import static com.google.code.mojo.license.util.FileContent.*;
import static com.google.code.mojo.license.util.FileUtils.*;
import static org.codehaus.plexus.util.FileUtils.*;

import java.io.File;
import java.io.IOException;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Document
{
    private final File file;
    private final DocumentType documentType;
    private final HeaderType headerType;
    private final String encoding;

    private Document(File file, HeaderType headerType, String encoding)
    {
        this.file = file;
        this.documentType = fromExtension(extension(file.getName()));
        this.headerType = headerType;
        this.encoding = encoding;
    }

    public File getFile()
    {
        return file;
    }

    public DocumentType getDocumentType()
    {
        return documentType;
    }

    public boolean isNotSupported()
    {
        return headerType == HeaderType.UNKNOWN;
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
            throw new IllegalStateException("Cannot read file " + getFile() + ". Cause: " + e.getMessage(), e);
        }
    }

    public void updateHeader(Header header)
    {
        HeaderParser parser = createParser();
        removeHeaderIfExist(parser);
        parser.getFileContent().insert(parser.getBeginPosition(), header.buildForType(parser.getHeaderType()));
        parser.getFileContent().write();
    }

    public void removeHeader()
    {
        HeaderParser parser = createParser();
        removeHeaderIfExist(parser);
        parser.getFileContent().write();
    }
    
    public boolean is(Header header)
    {
        try
        {
            return header.getLocation().sameFile(this.file.toURI().toURL());
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error comparing document " + this.file + " with file " + file + ". Cause: " + e.getMessage(), e);
        }
    }

    private HeaderParser createParser()
    {
        return parseHeader(readFrom(file, encoding), headerType);
    }

    private void removeHeaderIfExist(HeaderParser parser)
    {
        if(parser.gotHeader())
        {
            parser.getFileContent().delete(parser.getBeginPosition(), parser.getEndPosition());
        }
    }

    public static Document newDocument(File file, HeaderType headerType, String encoding)
    {
        return new Document(file, headerType, encoding);
    }

}

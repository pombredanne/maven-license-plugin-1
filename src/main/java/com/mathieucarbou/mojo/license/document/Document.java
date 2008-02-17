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
import static com.mathieucarbou.mojo.license.util.FileUtils.*;
import static org.codehaus.plexus.util.FileUtils.*;

import java.io.IOException;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Document
{
    private final String file;
    private final DocumentType documentType;
    private final CommentType commentType;

    private Document(String file, CommentType commentType)
    {
        this.file = file;
        this.documentType = fromExtension(extension(file));
        this.commentType = commentType == null ? documentType.getDefaultCommentType() : commentType;
    }

    private Document(String file)
    {
        this(file, null);
    }

    public String getFile()
    {
        return file;
    }

    public DocumentType getDocumentType()
    {
        return documentType;
    }

    public boolean isUnknownType()
    {
        return documentType == UNKNOWN;
    }

    public boolean hasHeader(Header header)
    {
        try
        {
            String fileHeader = readFirstLines(getFile(), header.getLineCount() + 10);
            String fileHeaderOneLine = remove(fileHeader, commentType.getFirstLine().trim(), commentType.getEndLine().trim(), commentType.getEachLine().trim(), "\n", "\r", "\t", " ");
            return fileHeaderOneLine.contains(header.asOneLineString());
        }
        catch(IOException e)
        {
            throw new IllegalStateException("Cannot read file " + getFile(), e);
        }
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Document");
        sb.append("{file=").append(file);
        sb.append(",documentType=").append(getDocumentType());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Document that = (Document) o;
        return !(file != null ? !file.equals(that.file) : that.file != null);
    }

    @Override
    public int hashCode()
    {
        return (file != null ? file.hashCode() : 0);
    }

    public static Document newDocument(String file)
    {
        return new Document(file);
    }

    public static Document newDocument(String file, CommentType commentType)
    {
        return new Document(file, commentType);
    }
}

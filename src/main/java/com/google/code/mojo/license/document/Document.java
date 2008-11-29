/**
 * Copyright (C) 2008 http://code.google.com/p/maven-license-plugin/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.mojo.license.document;

import com.google.code.mojo.license.header.Header;
import com.google.code.mojo.license.header.HeaderDefinition;
import com.google.code.mojo.license.header.HeaderParser;
import com.google.code.mojo.license.header.HeaderType;
import com.google.code.mojo.license.util.FileContent;
import com.google.code.mojo.license.util.FileUtils;
import static com.google.code.mojo.license.util.FileUtils.readFirstLines;
import static com.google.code.mojo.license.util.FileUtils.remove;

import java.io.File;
import java.io.IOException;

/**
 * <b>Date:</b> 16-Feb-2008<br> <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Document {
    private final File file;
    private final HeaderDefinition headerDefinition;
    private final String encoding;
    private HeaderParser parser;

    public Document(File file, HeaderDefinition headerDefinition, String encoding) {
        this.file = file;
        this.headerDefinition = headerDefinition;
        this.encoding = encoding;
    }

    public HeaderDefinition getHeaderDefinition() {
        return headerDefinition;
    }

    public File getFile() {
        return file;
    }

    public boolean isNotSupported() {
        if (headerDefinition == null) {
            return true;
        }
        return HeaderType.UNKNOWN.getDefinition().getType().equals(headerDefinition.getType());
    }

    public boolean hasHeader(Header header) {
        try {
            String fileHeader = readFirstLines(file, header.getLineCount() + 10);
            String fileHeaderOneLine = remove(fileHeader, headerDefinition.getFirstLine().trim(), headerDefinition.getEndLine().trim(), headerDefinition.getBeforeEachLine().trim(), "\n", "\r", "\t", " ");
            return fileHeaderOneLine.contains(remove(header.asOneLineString(), headerDefinition.getFirstLine().trim(), headerDefinition.getEndLine().trim(), headerDefinition.getBeforeEachLine().trim()));
        }
        catch (IOException e) {
            throw new IllegalStateException("Cannot read file " + getFile() + ". Cause: " + e.getMessage(), e);
        }
    }

    public void updateHeader(Header header) {
        createParser();
        removeHeaderIfExist();
        String headerStr = header.buildForDefinition(parser.getHeaderDefinition(), parser.getFileContent().isUnix());
        parser.getFileContent().insert(parser.getBeginPosition(), headerStr);
    }

    public void save() {
        if (parser != null) {
            try {
                FileUtils.write(file, parser.getFileContent().getContent(), encoding);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot write new header in file " + getFile() + ". Cause: " + e.getMessage(), e);
            }
        }
    }

    public String getContent() {
        return parser == null ? "" : parser.getFileContent().getContent();
    }

    public void removeHeader() {
        createParser();
        removeHeaderIfExist();
    }

    public boolean is(Header header) {
        try {
            return header.getLocation().sameFile(this.file.toURI().toURL());
        }
        catch (Exception e) {
            throw new RuntimeException("Error comparing document " + this.file + " with file " + file + ". Cause: " + e.getMessage(), e);
        }
    }

    private void createParser() {
        if (parser == null) {
            parser = new HeaderParser(new FileContent(file, encoding), headerDefinition);
        }
    }

    private void removeHeaderIfExist() {
        if (parser.gotAnyHeader()) {
            parser.getFileContent().delete(parser.getBeginPosition(), parser.getEndPosition());
        }
    }
}

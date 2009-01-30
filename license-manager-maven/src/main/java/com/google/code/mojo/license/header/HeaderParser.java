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

package com.google.code.mojo.license.header;

import com.google.code.mojo.license.util.FileContent;

/**
 * The <code>HeaderParser</code> class is used to get header information about the current header defined in the given
 * file.<br/> The achieve this it will use the <code>HeaderDefinition</code> associated to the type of the given file.
 * <p/>
 * Important: is considered a license header a header which contains the word <em>copyright</em> (case insensitive)
 * within a section of the file which match the given <code>HeaderDefinition</code> associated to this
 * <code>HeaderParser</code>.
 *
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 * @see com.google.code.mojo.license.header.HeaderDefinition
 */
public final class HeaderParser {
    private final int beginPosition;
    private final int endPosition;
    private final boolean existingHeader;
    private final FileContent fileContent;
    private boolean firstLineSkipped;
    private HeaderDefinition headerDefinition;

    private String line;

    /**
     * Creates a <code>HeaderParser</code> object linked to the given file content and the associated header definition
     * based on the file type.
     *
     * @param fileContent      The file content.
     * @param headerDefinition The associated header definition to use.
     * @throws IllegalArgumentException If the file content is null or if the header definition is null.
     */
    public HeaderParser(FileContent fileContent, HeaderDefinition headerDefinition) {
        if (fileContent == null) {
            throw new IllegalArgumentException("Cannot create a header parser for null file content");
        }
        if (headerDefinition == null) {
            throw new IllegalArgumentException("Cannot work on file header if the header definition is null");
        }
        this.headerDefinition = headerDefinition;
        this.fileContent = fileContent;
        beginPosition = findBeginPosition();
        existingHeader = hasHeader();
        endPosition = existingHeader ? findEndPosition() : -1;
    }

    /**
     * Returns the index position in the content where the header effectively starts.
     *
     * @return The index in the content.
     */
    public int getBeginPosition() {
        return beginPosition;
    }

    /**
     * Returns the index position in the content where the header effectively ends.
     *
     * @return The index in the content.
     */
    public int getEndPosition() {
        return endPosition;
    }

    /**
     * Tells if the given file already contains a license header.
     *
     * @return true if a license header has been detect or false.
     */
    public boolean gotAnyHeader() {
        return existingHeader;
    }

    /**
     * Returns the file content.
     *
     * @return The content.
     */
    public FileContent getFileContent() {
        return fileContent;
    }

    /**
     * Returns the header definition associated to this header parser (itself bounded to a file).
     *
     * @return The associated header definition.
     */
    public HeaderDefinition getHeaderDefinition() {
        return headerDefinition;
    }

    private int findBeginPosition() {
        int beginPosition = 0;
        line = fileContent.nextLine();
        if (line != null && headerDefinition.mustSkip(line)) {
            beginPosition = fileContent.getPosition();
            line = fileContent.nextLine();
            firstLineSkipped = true;
        }
        return beginPosition;
    }

    private boolean hasHeader() {
        // fix for issue 10 - freemarker supports two patterns of comments
        if (HeaderType.fromName(headerDefinition.getType()) == HeaderType.FTL && firstLineSkipped) {
            headerDefinition = HeaderType.FTL_ALT.getDefinition();
        }

        // skip blank lines
        while (line != null && "".equals(line.trim())) {
            line = fileContent.nextLine();
        }

        // check if there is already a header
        boolean gotHeader = false;
        if (headerDefinition.isFirstHeaderLine(line)) {
            StringBuilder existingHeader = new StringBuilder();
            do {
                existingHeader.append(line.toLowerCase());
                line = fileContent.nextLine();
            }
            while (line != null && ("".equals(line) || !headerDefinition.isLastHeaderLine(line) || line.startsWith(headerDefinition.getBeforeEachLine())));
            gotHeader = existingHeader.indexOf("copyright") != -1;
            existingHeader.setLength(0);
            existingHeader = null;
        }
        return gotHeader;
    }

    private int findEndPosition() {
        // we check if there is a header, if the next line is the blank line of the header
        int end = fileContent.getPosition();
        line = fileContent.nextLine();
        if (line != null && "".equals(line.trim())) {
            end = fileContent.getPosition();
        }
        return end;
    }
}

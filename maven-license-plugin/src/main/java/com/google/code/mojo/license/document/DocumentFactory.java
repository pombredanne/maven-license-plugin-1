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

import com.google.code.mojo.license.header.HeaderDefinition;
import static org.codehaus.plexus.util.FileUtils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>Date:</b> 14-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentFactory {
    private final Map<String, String> mapping;
    private final Map<String, HeaderDefinition> definitions;
    private final File basedir;
    private final String encoding;

    public DocumentFactory(File basedir, Map<String, String> mapping, Map<String, HeaderDefinition> definitions, String encoding) {
        this.mapping = mapping;
        this.definitions = definitions;
        this.basedir = basedir;
        this.encoding = encoding;
    }

    public Document[] createDocuments(String[] files) {
        List<Document> wrappers = new ArrayList<Document>(files.length);
        for (String file : files) {
            wrappers.add(getWrapper(file, encoding));
        }
        return wrappers.toArray(new Document[wrappers.size()]);
    }

    private Document getWrapper(String file, String encoding) {
        String headerType = mapping.get(extension(file).toLowerCase());
        if (headerType == null) {
            headerType = mapping.get("");
        } else {
            headerType = headerType.toLowerCase();
        }
        return new Document(new File(basedir, file), definitions.get(headerType), encoding);
    }
}

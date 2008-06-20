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

import static com.google.code.mojo.license.document.Document.*;
import com.google.code.mojo.license.header.HeaderType;
import static org.codehaus.plexus.util.FileUtils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>Date:</b> 14-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentFactory
{
    private final Map<String, String> mapping;
    private final File basedir;
    private final String encoding;

    private DocumentFactory(File basedir, Map<String, String> mapping, String encoding)
    {
        this.mapping = mapping;
        this.basedir = basedir;
        this.encoding = encoding;
    }

    public Document[] wrap(String[] files)
    {
        List<Document> wrappers = new ArrayList<Document>(files.length);
        for(String file : files)
        {
            wrappers.add(getWrapper(file, encoding));
        }
        return wrappers.toArray(new Document[wrappers.size()]);
    }

    private Document getWrapper(String file, String encoding)
    {
        return newDocument(new File(basedir, file), HeaderType.fromName(mapping.get(extension(file))), encoding);
    }

    public static DocumentFactory newDocumentFactory(File basedir, Map<String, String> mapping, String encoding)
    {
        return new DocumentFactory(basedir, mapping, encoding);
    }

}

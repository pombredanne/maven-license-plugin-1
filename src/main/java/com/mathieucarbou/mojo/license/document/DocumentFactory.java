/**
 * Copyright (C) 2008 Mathieu Carbou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this document except in compliance with the License.
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

import static com.mathieucarbou.mojo.license.document.DocumentMapping.*;
import org.apache.maven.plugin.MojoExecutionException;
import static org.codehaus.plexus.util.FileUtils.*;

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

    private DocumentFactory(Map<String, String> mapping)
    {
        this.mapping = mapping;
    }

    public Document[] wrap(String[] files) throws MojoExecutionException
    {
        List<Document> wrappers = new ArrayList<Document>(files.length);
        for(String file : files)
        {
            wrappers.add(getWrapper(file));
        }
        return wrappers.toArray(new Document[wrappers.size()]);
    }

    private Document getWrapper(String file) throws MojoExecutionException
    {
        String extension = extension(file);
        String fallbackType = mapping.get(extension);
        Class<? extends Document> wrapper = getExtensionWrapper(fallbackType);
        try
        {
            return wrapper.getConstructor(String.class).newInstance(file);
        }
        catch(Exception e)
        {
            throw new MojoExecutionException("Internal error: cannot create new instance of class " + wrapper + " with a String parameter (as file path)", e);
        }
    }

    public static DocumentFactory newDocumentFactory(Map<String, String> mapping)
    {
        return new DocumentFactory(mapping);
    }

}

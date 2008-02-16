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

import com.mathieucarbou.mojo.license.document.impl.JavaDocument;
import com.mathieucarbou.mojo.license.document.impl.UnknownDocument;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentMapping
{
    private static final Map<String, Class<? extends Document>> documentClass = new HashMap<String, Class<? extends Document>>();

    static
    {
        documentClass.put("java", JavaDocument.class);
    }

    public static Map<String, String> defaultDocumentMapping()
    {
        return new HashMap<String, String>()
        {
            {
                put("java", "java");
                put("groovy", "java");
            }
        };
    }

    public static Class<? extends Document> getExtensionWrapper(String extension)
    {
        Class<? extends Document> clazz = documentClass.get(extension);
        return clazz == null ? UnknownDocument.class : clazz;
    }
}

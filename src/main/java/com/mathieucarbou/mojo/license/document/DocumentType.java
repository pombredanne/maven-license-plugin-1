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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public enum DocumentType
{
    UNKNOWN(UnknownDocument.extension(), UnknownDocument.class),
    JAVA(JavaDocument.extension(), JavaDocument.class),
    GROOVY("groovy", JavaDocument.class);

    private static final Map<String, String> mapping = new HashMap<String, String>(3);

    static
    {
        for(DocumentType type : values())
        {
            Class<? extends Document> wrapperClass = type.getWrapperClass();
            try
            {
                String extension = (String) wrapperClass.getMethod("extension").invoke(null);
                mapping.put(type.getFileExtension(), extension);
            }
            catch(Exception e)
            {
                throw new AssertionError("Internal error: wrapper class implementing Document should have a static method extension()");
            }
        }
    }

    private final String fileExtension;
    private final Class<? extends Document> wrapperClass;

    private DocumentType(String fileExtension, Class<? extends Document> wrapperClass)
    {
        this.fileExtension = fileExtension;
        this.wrapperClass = wrapperClass;
    }

    public String getFileExtension()
    {
        return fileExtension;
    }

    public Class<? extends Document> getWrapperClass()
    {
        return wrapperClass;
    }

    @Override
    public String toString()
    {
        return getFileExtension();
    }

    public static DocumentType fromExtension(String extension)
    {
        for(DocumentType type : values())
        {
            if(type.getFileExtension().equalsIgnoreCase(extension))
            {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static Map<String, String> defaultMapping()
    {
        return Collections.unmodifiableMap(mapping);
    }
}

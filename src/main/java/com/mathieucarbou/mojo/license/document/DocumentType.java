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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Date:</b> 16-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public enum DocumentType
{
    ////////// DOCUMENT TYPES //////////

    JAVA("java",            CommentType.JAVA),
    GROOVY("groovy",        CommentType.JAVA),
    JS("js",                CommentType.JAVA),
    CSS("css",              CommentType.JAVA),
    XML("xml",              CommentType.XML),
    DTD("dtd",              CommentType.XML),
    XSD("xsd",              CommentType.XML),
    FML("fml",              CommentType.XML),
    XSL("xsl",              CommentType.XML),
    HTML("html",            CommentType.XML),
    HTM("htm",              CommentType.XML),
    APT("apt",              CommentType.APT),
    PROPERTIES("properties", CommentType.PROPERTIES),
    SH("sh",                CommentType.PROPERTIES),
    TXT("txt",              CommentType.TXT),
    BAT("bat",              CommentType.CMD),
    CMD("cmd",              CommentType.CMD),
    UNKNOWN("unknown",      CommentType.UNKNOWN);

    ////////////////////////////////////

    private static final Map<String, String> mapping = new HashMap<String, String>(values().length);

    static
    {
        for(DocumentType type : values())
        {
            mapping.put(type.getExtension(), type.getDefaultCommentType().name());
        }
    }

    private final String extension;
    private final CommentType defaultCommentType;

    private DocumentType(String extension, CommentType defaultCommentType)
    {
        this.extension = extension;
        this.defaultCommentType = defaultCommentType;
    }

    public String getExtension()
    {
        return extension;
    }

    public CommentType getDefaultCommentType()
    {
        return defaultCommentType;
    }

    @Override
    public String toString()
    {
        return extension;
    }

    public static DocumentType fromExtension(String extension)
    {
        for(DocumentType type : values())
        {
            if(type.getExtension().equalsIgnoreCase(extension))
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

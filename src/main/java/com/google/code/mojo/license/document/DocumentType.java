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

import com.google.code.mojo.license.header.HeaderType;

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

    JAVA("java",            HeaderType.JAVA),
    GROOVY("groovy",        HeaderType.JAVA),
    JS("js",                HeaderType.JAVA),
    CSS("css",              HeaderType.JAVA),
    XML("xml",              HeaderType.XML),
    DTD("dtd",              HeaderType.XML),
    XSD("xsd",              HeaderType.XML),
    FML("fml",              HeaderType.XML),
    XSL("xsl",              HeaderType.XML),
    HTML("html",            HeaderType.XML),
    HTM("htm",              HeaderType.XML),
    APT("apt",              HeaderType.APT),
    PROPERTIES("properties", HeaderType.PROPERTIES),
    SH("sh",                HeaderType.PROPERTIES),
    TXT("txt",              HeaderType.TEXT),
    BAT("bat",              HeaderType.BATCH),
    CMD("cmd",              HeaderType.BATCH),
    SQL("sql",              HeaderType.SQL),
    JSP("jsp",              HeaderType.JSP),
    FREEMARKER("ftl",       HeaderType.FTL),
    UNKNOWN("unknown",      HeaderType.UNKNOWN);

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
    private final HeaderType defaultHeaderType;

    private DocumentType(String extension, HeaderType defaultHeaderType)
    {
        this.extension = extension;
        this.defaultHeaderType = defaultHeaderType;
    }

    public String getExtension()
    {
        return extension;
    }

    public HeaderType getDefaultCommentType()
    {
        return defaultHeaderType;
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

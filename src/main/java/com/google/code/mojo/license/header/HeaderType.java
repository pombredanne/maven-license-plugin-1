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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines the default header definitions available out of the box within this plugin.
 *
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 * @author Cedric Pronzato
 * @see com.google.code.mojo.license.header.HeaderDefinition
 */
public enum HeaderType {
    ////////// COMMENT TYPES //////////

    //              FirstLine           Before              EndLine             SkipLine                    FirstLineDetection              LastLineDetection
    //generic
    JAVADOC_STYLE   ("/**",             " * ",              " */",              null,                       "(\\s|\\t)*/\\*.*$",            ".*\\*/(\\s|\\t)*$"),
    SCRIPT_STYLE    ("#",               "# ",               "#",                "^#!.*$",                   "#$",                           "#$"),
    XML_STYLE       ("<!--EOL",         "    ",             "EOL-->",           "^<\\?xml.*>$",             "(\\s|\\t)*<!--.*$",            ".*-->(\\s|\\t)*$"),
    SEMICOLON_STYLE (";",               "; ",               ";",                null,                       ";$",                           ";$"),
    APOSTROPHE_STYLE("'",               "' ",               "'",                null,                       "'$",                           "'$"),
    EXCLAMATION_STYLE("!",              "! ",               "!",                null,                       "!$",                           "!$"),
    DOUBLEDASHES_STYLE("--",            "-- ",              "--",               null,                       "--$",                          "--$"),
    SLASHSTAR_STYLE("/*",               " * ",              " */",              null,                       "(\\s|\\t)*/\\*.*$",            ".*\\*/(\\s|\\t)*$"),
    BRACESSTAR_STYLE("\\{*",            " * ",              " *\\}",            null,                       "(\\s|\\t)*\\{\\*.*$",          ".*\\*\\}(\\s|\\t)*$"),
    SHARPSTAR_STYLE ("#*",              " * ",              " *#",              null,                       "(\\s|\\t)*#\\*.*$",            ".*\\*#(\\s|\\t)*$"),
    DOUBLETILDE_STYLE("~~",             "~~ ",              "~~",               null,                       "~~$",                          "~~$"),
    DYNASCRIPT_STYLE("<%--EOL",         "    ",             "EOL--%>",          null,                       "(\\s|\\t)*<%--.*$",            ".*--%>(\\s|\\t)*$"),
    DYNASCRIPT3_STYLE("<!---EOL",       "    ",             "EOL--->",          null,                       "(\\s|\\t)*<!---.*$",           ".*--->(\\s|\\t)*$"),
    PERCENT3_STYLE  ("%%%",             "%%% ",             "%%%",              null,                       "%%%$",                         "%%%$"),
    EXCLAMATION3_STYLE("!!!",           "!!! ",             "!!!",              null,                       "!!!$",                         "!!!$"),

    DOUBLESLASH_STYLE("//",             "// ",              "//",               null,                       "//$",                          "//$"),
    // non generic
    PHP             ("/*",              " * ",              " */",              "^<\\?php.*$",              "(\\s|\\t)*/\\*.*$",            ".*\\*/(\\s|\\t)*$"),
    ASP             ("<%",              "    ",             "%>",               null,                       "(\\s|\\t)*<%[^@].*$",          ".*%>(\\s|\\t)*$"),
    LUA             ("--[[EOL",         "    ",             "EOL]]",            null,                       "--\\[\\[$",                    "\\]\\]$"),
    FTL             ("<#--EOL",         "    ",             "EOL-->",           "\\[#ftl(\\s.*)?\\]",       "(\\s|\\t)*<#--.*$",            ".*-->(\\s|\\t)*$"),
    FTL_ALT         ("[#--EOL",         "    ",             "EOL--]",           "\\[#ftl(\\s.*)?\\]",       "(\\s|\\t)*\\[#--.*$",          ".*--\\](\\s|\\t)*$"),
    TEXT            ("====",            "    ",             "====",             null,                       "====$",                        "====$"),
    BATCH           ("@REM",            "@REM ",            "@REM",             null,                       "@REM$",                        "@REM$"),
    // unknown
    UNKNOWN         ("",                "",                 "",                 null,                       null,                           null);

    ////////////////////////////////////

    private static final Map<String, HeaderDefinition> definitions = new HashMap<String, HeaderDefinition>(values().length);

    static {
        for (HeaderType type : values()) {
            definitions.put(type.getDefinition().getType(), type.getDefinition());
        }
    }

    private final HeaderDefinition definition;

    private HeaderType(String firstLine, String beforeEachLine, String endLine, String skipLinePattern, String firstLineDetectionPattern, String endLineDetectionPattern) {
        definition = new HeaderDefinition(this.name().toLowerCase(), firstLine, beforeEachLine, endLine, skipLinePattern, firstLineDetectionPattern, endLineDetectionPattern);
    }

    /**
     * Returns the <code>HeaderDefinition</code> which corresponds to this enumeration instance.
     *
     * @return The header definition.
     */
    public HeaderDefinition getDefinition() {
        return definition;
    }

    /**
     * Returns the <code>HeaderType</code> declared in this enumeration for the given header type name.
     *
     * @param name The header definition type name.
     * @return The <code>HeaderType</code> declared in this enumeration if found or {@link
     *         com.google.code.mojo.license.header.HeaderType#UNKNOWN}.
     */
    public static HeaderType fromName(String name) {
        for (HeaderType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    /**
     * Returns the header definitions of every default definitions declared by this enumeration as a map using the
     * header type name as key.
     *
     * @return The default definitions declared by this enumeration.
     */
    public static Map<String, HeaderDefinition> defaultDefinitions() {
        return Collections.unmodifiableMap(definitions);
    }
}

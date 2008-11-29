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

import com.google.code.mojo.license.util.FileUtils;
import com.google.code.xmltool.XMLDoc;
import com.google.code.xmltool.XMLDocument;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class AdditionalHeaderDefinitionTest {
    @Test
    public void test_load_definitions() throws Exception {
        XMLDocument def = XMLDoc.newDocument().addRoot("additionalHeaders")
                .addTag("xquery")
                .addTag("firstLine").addText("(:")
                .addTag("beforeEachLine").addText(" : ")
                .addTag("endLine").addText(" :)")
                .addTag("firstLineDetectionPattern").addText("\\(\\:")
                .addTag("lastLineDetectionPattern").addText("\\:\\)");

        System.out.println(def.toString());

        AdditionalHeaderDefinition loader = new AdditionalHeaderDefinition(def);

        assertEquals(loader.getDefinitions().size(), 1);
        assertEquals(loader.getDefinitions().get("xquery").getType(), "xquery");
        assertNull(loader.getDefinitions().get("xquery").getSkipLinePattern());

        Map<String, String> props = new HashMap<String, String>();
        props.put("year", "2008");
        Header header = new Header(getClass().getResource("/test-header1.txt"), props);

        //FileUtils.write(new File("src/test/resources/test-header3.txt"), header.buildForDefinition(loader.getDefinitions().get("xquery")));

        final String content = FileUtils.read(new File("src/test/resources/test-header3.txt"), System.getProperty("file.encoding"));
        assertEquals(header.buildForDefinition(loader.getDefinitions().get("xquery"), content.indexOf("\n") == -1),
                content);
    }

    @Test
    public void test_load_definitions2() throws Exception {
        XMLDocument def = XMLDoc.newDocument().addRoot("additionalHeaders")
                .addTag("text")
                .addTag("firstLine").addText(":(")
                .addTag("beforeEachLine").addText(" ( ")
                .addTag("endLine").addText(":(")
                .addTag("firstLineDetectionPattern").addText("\\:\\(")
                .addTag("lastLineDetectionPattern").addText("\\:\\(");

        System.out.println(def.toString());

        AdditionalHeaderDefinition loader = new AdditionalHeaderDefinition(def);
        
        Map<String, String> props = new HashMap<String, String>();
        props.put("year", "2008");
        Header header = new Header(getClass().getResource("/check/header.txt"), props);

        System.out.println(header.buildForDefinition(loader.getDefinitions().get("text"), false));
    }
}

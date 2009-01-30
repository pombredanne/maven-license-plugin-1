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
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;
import static java.lang.String.format;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DefaultHeaderDefinitionTest {
    @Test
    public void test_styles() throws Exception {
        Map<String, String> props = new HashMap<String, String>();
        props.put("year", "2008");
        Header header = new Header(getClass().getResource("/test-header1.txt"), props);
        for (HeaderDefinition definition : HeaderType.defaultDefinitions().values()) {

            //FileUtils.write(new File(format("src/test/resources/styles/%s.txt", definition.getType())), header.buildForDefinition(definition, false));

            final String content = FileUtils.read(new File(format("src/test/resources/styles/%s.txt", definition.getType())), System.getProperty("file.encoding"));
            assertEquals(content, header.buildForDefinition(definition, content.indexOf("\n") == -1),
                    "Bad header for type: " + definition.getType());
        }
    }
}
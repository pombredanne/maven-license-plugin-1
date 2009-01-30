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

package com.google.code.mojo.license;

import com.google.code.mojo.license.document.Document;
import com.google.code.mojo.license.header.Header;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Remove the specified header from source files
 *
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 * @goal remove
 */
public class LicenseRemoveMojo extends AbstractLicenseMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Removing license headers...");

        execute(new Callback() {
            public void onHeaderNotFound(Document document, Header header) {
                debug("Header was not found in: %s (But keep trying to find another header to remove)", document.getFile());
                document.removeHeader();
                document.save();
            }

            public void onExistingHeader(Document document, Header header) {
                info("Removing license header from: %s", document.getFile());
                document.removeHeader();
                document.save();
            }
        });
    }

}

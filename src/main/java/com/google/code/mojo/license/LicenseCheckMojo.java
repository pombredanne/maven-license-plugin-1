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

package com.google.code.mojo.license;

import com.google.code.mojo.license.document.Document;
import com.google.code.mojo.license.header.Header;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.util.ArrayList;
import java.util.List;

/**
 * Check if the source files of the project have a valid license header
 *
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 * @goal check
 * @phase verify
 * @date 13-Feb-2008
 */
public class LicenseCheckMojo extends AbstractLicenseMojo
{

    /**
     * Whether to fail the build if some file miss license header
     *
     * @parameter expression="${license.failIfMissing}" default-value="true"
     */
    protected boolean failIfMissing = true;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        getLog().info("Checking licenses...");
        final List<Document> missingHeaders = new ArrayList<Document>();

        execute(new Callback()
        {
            public void onHeaderNotFound(Document document, Header header)
            {
                info("Missing header in: %s", document.getFile());
                missingHeaders.add(document);
            }

            public void onExistingHeader(Document document, Header header)
            {
                debug("Header OK in: %s", document.getFile());
            }
        });
        
        if(!missingHeaders.isEmpty())
        {
            if(failIfMissing)
            {
                throw new MojoFailureException("Some files do not have the expected license header");
            }
            else
            {
                getLog().warn("Some files do not have the expected license header");
            }
        }
    }

}

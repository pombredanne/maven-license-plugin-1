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

package com.mathieucarbou.mojo.license;

import com.mathieucarbou.mojo.license.document.Document;
import com.mathieucarbou.mojo.license.document.Header;
import static com.mathieucarbou.mojo.license.document.Header.*;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Reformat files with a missing header to add it
 *
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 * @goal format
 * @date 13-Feb-2008
 */
public class LicenseFormatMojo extends AbstractLicenseMojo
{

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        getLog().info("Adding license header when missing...");

        Header header = headerFromFile(this.header);
        debug("Header %s:\n%s", header.getFile(), header);

        for(Document document : selectedDocuments())
        {
            if(!document.isSupported())
            {
                warn("Unknown file extension: %s", document.getFile());
            }
            else if(document.hasHeader(header))
            {
                debug("Header OK in: %s", document.getFile());
            }
            else
            {
                info("Adding license header in: %s", document.getFile());
                document.updateHeader(header);
            }
        }
    }

}
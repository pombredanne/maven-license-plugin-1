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
import static com.mathieucarbou.mojo.license.document.DocumentFactory.*;
import static com.mathieucarbou.mojo.license.document.DocumentType.*;
import com.mathieucarbou.mojo.license.document.Header;
import static com.mathieucarbou.mojo.license.document.Header.*;
import com.mathieucarbou.mojo.license.util.Selection;
import static com.mathieucarbou.mojo.license.util.Selection.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import static java.lang.String.*;
import java.util.ArrayList;
import static java.util.Arrays.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Check if the source files of the project have a valid license header 
 *
 * @goal check
 * @phase verify
 * @date 13-Feb-2008
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class LicenseCheckMojo extends AbstractMojo
{
    /**
     * Specifies files, which are included in the check. By default, all files are included.
     *
     * @parameter
     */
    protected String[] includes = new String[0];

    /**
     * Specifies files, which are excluded in the check. By default, no files are excluded.
     *
     * @parameter
     */
    protected String[] excludes = new String[0];

    /**
     * Set this to "true" to cause no output. Defaults to false.
     *
     * @parameter expression="${license.quiet}" default-value="false"
     */
    protected boolean quiet = false;

    /**
     * The base directory, in which to search for files.
     *
     * @parameter expression="${license.basedir}" default-value="${basedir}"
     * @required
     */
    protected File basedir;

    /**
     * The text document containing the license header to check or use for reformatting
     *
     * @parameter expression="${license.header}"
     * @required
     */
    protected File header;

    /**
     * Whether to use the default excludes when scanning for files.
     *
     * @parameter expression="${license.useDefaultExcludes}" default-value="true"
     */
    protected boolean useDefaultExcludes = true;

    /**
     * Set mapping between document mapping and a supported type to use
     *
     * @parameter
     */
    protected Map<String, String> mapping = new HashMap<String, String>();

    /**
     * Whether to use the default mapping between fiel extensions and comments to use, or only the one your provide
     *
     * @parameter expression="${license.useDefaultMapping}" default-value="true"
     */
    protected boolean useDefaultMapping = true;

    /**
     * Whether to fail the build if some file miss license header
     *
     * @parameter expression="${license.failIfMissingHeaders}" default-value="true"
     */
    protected boolean failIfMissingHeaders = true;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        getLog().info("Checking licenses...");

        Header header = headerFromFile(this.header);

        debug("Header %s:\n%s", header.getFile(), header);

        Selection selection = newSelection(basedir, includes, excludes, useDefaultExcludes);

        debug("Including: %s", deepToString(selection.getIncluded()));
        debug("Excluding: %s", deepToString(selection.getExcluded()));

        Document[] documents = newDocumentFactory(buildMapping()).wrap(selection.getSelectedFiles());

        for(Document document : documents)
        {
            debug("Selected file: %s [type=%s]", document.getFile(), document.getDocumentType());
        }

        List<Document> missingHeaders = new ArrayList<Document>();

        for(Document document : documents)
        {
            if(!document.canCheck())
            {
                warn("Unknown file extension: %s", document.getFile());
            }
            else if(document.hasHeader(header))
            {
                debug("Header OK in: %s", document.getFile());
            }
            else
            {
                info("Missing header in: %s", document.getFile());
                missingHeaders.add(document);
            }
        }

        if(!missingHeaders.isEmpty())
        {
            if(failIfMissingHeaders)
            {
                throw new MojoFailureException("Some files do not have the expected license header.");
            }
            else
            {
                getLog().warn("Some files do not have the expected license header.");
            }
        }
    }

    protected Map<String, String> buildMapping()
    {
        Map<String, String> extensionMapping = useDefaultMapping ? new HashMap<String, String>(defaultMapping()) : new HashMap<String, String>();
        extensionMapping.putAll(mapping);
        return extensionMapping;
    }

    protected void info(String format, Object... params)
    {
        if(!quiet)
        {
            getLog().info(format(format, params));
        }
    }

    protected void debug(String format, Object... params)
    {
        getLog().debug(format(format, params));
    }

    protected void warn(String format, Object... params)
    {
        getLog().warn(format(format, params));
    }
}

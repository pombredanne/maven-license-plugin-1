/**
 * Copyright (C) 2008 Mathieu Carbou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this document except in compliance with the License.
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

import static com.mathieucarbou.mojo.license.Header.*;
import com.mathieucarbou.mojo.license.document.Document;
import static com.mathieucarbou.mojo.license.document.DocumentFactory.*;
import static com.mathieucarbou.mojo.license.document.DocumentMapping.*;
import com.mathieucarbou.mojo.license.util.Selection;
import static com.mathieucarbou.mojo.license.util.Selection.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import static java.lang.String.*;
import static java.util.Arrays.*;
import java.util.Map;

/**
 * <b>Date:</b> 13-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 *
 * @goal document
 * @phase verify
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
    protected File headerFile;

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
    protected Map<String, String> mapping = defaultDocumentMapping();

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        info("Checking license on source files...");

        Header header = headerFromFile(headerFile);

        debug("Header:\n%s", header.asString());

        Selection selection = newSelection(basedir, includes, excludes, useDefaultExcludes);

        info("Including: %s", deepToString(selection.getIncluded()));
        info("Excluding: %s", deepToString(selection.getExcluded()));

        String[] selected = selection.getSelectedFiles();
        for(String file : selected)
        {
            debug("Checking document: %s", file);
        }

        Document[] documents = newDocumentFactory(mapping).wrap(selected);
        for(Document document : documents)
        {
            debug(document.toString());
        }
    }

    protected void info(String format, Object... params)
    {
        getLog().info(format(format, params));
    }

    protected void debug(String format, Object... params)
    {
        getLog().debug(format(format, params));
    }
}

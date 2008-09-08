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
import static com.google.code.mojo.license.document.DocumentFactory.*;
import static com.google.code.mojo.license.document.DocumentType.*;
import com.google.code.mojo.license.header.Header;
import static com.google.code.mojo.license.header.Header.*;
import com.google.code.mojo.license.util.Selection;
import static com.google.code.mojo.license.util.Selection.*;
import static com.google.code.mojo.license.util.resource.ResourceFactory.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import static java.lang.String.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import static java.util.Arrays.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Date:</b> 18-Feb-2008<br>
 * <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public abstract class AbstractLicenseMojo extends AbstractMojo
{

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
    protected String header;

    /**
     * The properties to use when reading the header, to replace tokens
     *
     * @parameter
     */
    protected Map<String, String> properties = new HashMap<String, String>();

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
     * Set this to "true" to cause no output
     *
     * @parameter expression="${license.quiet}" default-value="false"
     */
    protected boolean quiet = false;
    
    /**
     * Set the charcter encoding for files
     * 
     * @parameter expression="${license.encoding}" default-value="${file.encoding}"
     */
    protected String encoding = System.getProperty("file.encoding");

    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    protected MavenProject project = new MavenProject();

    @SuppressWarnings({"unchecked"})
    protected final void execute(Callback callback) throws MojoExecutionException, MojoFailureException
    {
        class CL extends URLClassLoader
        {
            CL()
            {
                super(new URL[0], Thread.currentThread().getContextClassLoader());
            }

            @Override
            public void addURL(URL url) {
                super.addURL(url);
            }
        }
        
        CL cl = new CL();
        URL location;
        try
        {
            List<String> cp = project.getCompileClasspathElements();
            for (String s : cp)
            {
                if(s != null)
                {
                    cl.addURL(new File(s).toURI().toURL());
                }
            }
            Thread.currentThread().setContextClassLoader(cl);
            location = newResourceFactory(basedir).findResource(this.header);
        }
        catch(Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        Header header = readFrom(location, mergeProperties());
        debug("Header %s:\n%s", header.getLocation(), header);
        for(Document document : selectedDocuments())
        {
            if(document.isNotSupported())
            {
                warn("Unknown file extension: %s", document.getFile());
            }
            else if(document.is(header))
            {
                debug("Skipping header file: %s", document.getFile());
            }
            else if(document.hasHeader(header))
            {
                callback.onExistingHeader(document, header);
            }
            else
            {
                callback.onHeaderNotFound(document, header);
            }
        }
    }

    protected final Map<String, String> mergeProperties()
    {
        // first put systen environment
        Map<String, String> properties = new HashMap<String, String>(System.getenv());
        // we override by properties in the POM
        if(this.properties != null)
        {
            properties.putAll(this.properties);
        }
        // then we override by java system properties (command-line -D...)
        for(Map.Entry<Object, Object> entry : System.getProperties().entrySet())
        {
            properties.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return properties;
    }

    protected final Document[] selectedDocuments()
    {
        Selection selection = newSelection(basedir, includes, buildExcludes(), useDefaultExcludes);
        debug("From: %s", basedir);
        debug("Including: %s", deepToString(selection.getIncluded()));
        debug("Excluding: %s", deepToString(selection.getExcluded()));
        Document[] documents = newDocumentFactory(basedir, buildMapping(), encoding).wrap(selection.getSelectedFiles());
        for(Document document : documents)
        {
            debug("Selected file: %s [type=%s]", document.getFile(), document.getDocumentType());
        }
        return documents;
    }

    @SuppressWarnings({"unchecked"})
    private String[] buildExcludes()
    {
        List<String> excludes = new ArrayList<String>();
        excludes.addAll(asList(this.excludes));
        if(project != null && project.getModules() != null)
        {
            for(String module : (List<String>) project.getModules())
            {
                excludes.add(module + "/**");
            }
        }
        return excludes.toArray(new String[excludes.size()]);
    }

    protected final void info(String format, Object... params)
    {
        if(!quiet)
        {
            getLog().info(format(format, params));
        }
    }

    protected final void debug(String format, Object... params)
    {
        getLog().debug(format(format, params));
    }

    protected final void warn(String format, Object... params)
    {
        getLog().warn(format(format, params));
    }

    private Map<String, String> buildMapping()
    {
        Map<String, String> extensionMapping = useDefaultMapping ? new HashMap<String, String>(defaultMapping()) : new HashMap<String, String>();
        extensionMapping.putAll(mapping);
        return extensionMapping;
    }

}

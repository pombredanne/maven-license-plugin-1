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
import com.google.code.mojo.license.document.DocumentFactory;
import com.google.code.mojo.license.document.DocumentType;
import static com.google.code.mojo.license.document.DocumentType.*;
import com.google.code.mojo.license.header.AdditionalHeaderDefinition;
import com.google.code.mojo.license.header.Header;
import com.google.code.mojo.license.header.HeaderDefinition;
import com.google.code.mojo.license.header.HeaderType;
import com.google.code.mojo.license.util.Selection;
import com.google.code.mojo.license.util.resource.ResourceFinder;
import com.mycila.xmltool.XMLDoc;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import static java.lang.String.*;
import java.util.ArrayList;
import static java.util.Arrays.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Date:</b> 18-Feb-2008<br> <b>Author:</b> Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public abstract class AbstractLicenseMojo extends AbstractMojo {

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
     */
    protected String header;

    /**
     * Allows the use of external header definitions files. These files are properties like.
     *
     * @parameter
     */
    protected String[] headerDefinitions = new String[0];

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
     * Wheter to treat multi-modules projects as only one project (true) or treat multi-module projects separately
     * (false, by default)
     *
     * @parameter expression="${license.aggregate}" default-value="false"
     */
    protected boolean aggregate = false;

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

    private ResourceFinder finder;

    @SuppressWarnings({"unchecked"})
    protected final void execute(Callback callback) throws MojoExecutionException, MojoFailureException {
        if (header == null) {
            info("No header file specified to check for license");
            return;
        }

        finder = new ResourceFinder(basedir);
        try {
            finder.setCompileClassPath(project.getCompileClasspathElements());
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        finder.setPluginClassPath(getClass().getClassLoader());

        Header header = new Header(finder.findResource(this.header), mergeProperties());
        debug("Header %s:\n%s", header.getLocation(), header);
        for (Document document : selectedDocuments()) {
            if (document.isNotSupported()) {
                warn("Unknown file extension: %s", document.getFile());
            } else if (document.is(header)) {
                debug("Skipping header file: %s", document.getFile());
            } else if (document.hasHeader(header)) {
                callback.onExistingHeader(document, header);
            } else {
                callback.onHeaderNotFound(document, header);
            }
        }
    }

    protected final Map<String, String> mergeProperties() {
        // first put systen environment
        Map<String, String> properties = new HashMap<String, String>(System.getenv());
        // we override by properties in the POM
        if (this.properties != null) {
            properties.putAll(this.properties);
        }
        // then we override by java system properties (command-line -D...)
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            properties.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return properties;
    }

    protected final Document[] selectedDocuments() throws MojoFailureException {
        Selection selection = new Selection(basedir, includes, buildExcludes(), useDefaultExcludes);
        debug("From: %s", basedir);
        debug("Including: %s", deepToString(selection.getIncluded()));
        debug("Excluding: %s", deepToString(selection.getExcluded()));
        Document[] documents = new DocumentFactory(basedir, buildMapping(), buildHeaderDefinitions(), encoding)
                .createDocuments(selection.getSelectedFiles());
        for (Document document : documents) {
            debug("Selected file: %s [header style: %s]", document.getFile(), document.getHeaderDefinition());
        }
        return documents;
    }

    @SuppressWarnings({"unchecked"})
    private String[] buildExcludes() {
        List<String> excludes = new ArrayList<String>();
        excludes.addAll(asList(this.excludes));
        if (project != null && project.getModules() != null && !aggregate) {
            for (String module : (List<String>) project.getModules()) {
                excludes.add(module + "/**");
            }
        }
        return excludes.toArray(new String[excludes.size()]);
    }

    protected final void info(String format, Object... params) {
        if (!quiet) {
            getLog().info(format(format, params));
        }
    }

    protected final void debug(String format, Object... params) {
        if (!quiet) {
            getLog().debug(format(format, params));
        }
    }

    protected final void warn(String format, Object... params) {
        getLog().warn(format(format, params));
    }

    private Map<String, String> buildMapping() {
        Map<String, String> extensionMapping = useDefaultMapping ? new HashMap<String, String>(defaultMapping()) : new HashMap<String, String>();
        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            extensionMapping.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());
        }
        // force inclusion of unknow item to manage unknown files
        extensionMapping.put(DocumentType.UNKNOWN.getExtension(), DocumentType.UNKNOWN.getDefaultHeaderTypeName());
        return extensionMapping;
    }

    private Map<String, HeaderDefinition> buildHeaderDefinitions() throws MojoFailureException {
        // like mappings, first get default definitions
        final Map<String, HeaderDefinition> headers = new HashMap<String, HeaderDefinition>(HeaderType.defaultDefinitions());
        // and then override them with those provided in properties file
        for (String resource : headerDefinitions) {
            final AdditionalHeaderDefinition fileDefinitions = new AdditionalHeaderDefinition(XMLDoc.from(finder.findResource(resource), true));
            final Map<String, HeaderDefinition> map = fileDefinitions.getDefinitions();
            debug("%d header definitions loaded from '%s'", map.size(), resource);
            headers.putAll(map);
        }
        // force inclusion of unknow item to manage unknown files
        headers.put(HeaderType.UNKNOWN.getDefinition().getType(), HeaderType.UNKNOWN.getDefinition());
        return headers;
    }

}

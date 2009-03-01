package com.mycila.license.core;

import com.mycila.license.core.style.HeaderStyles;
import com.mycila.license.core.type.DocumentTypes;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Configuration {

    /*private File baseDirectory = new File(".");
    private Set<String> includes = new HashSet<String>();
    private Set<String> excludes = new HashSet<String>();
    private boolean useDefaultExcludes = true;*/
    private final FileSelection fileSelection = new FileSelection();

    /*private URL header;
    private Pattern headerDetectionPattern = Pattern.compile("(?is).*copyright.*");
    private Map<String, Object> headerPlaceholders = new HashMap<String, Object>();*/
    private final LicenseHeader licenseHeader = new LicenseHeader();

    /*private boolean useDefaultHeaderStyles = true;
    private SortedMap<String, HeaderStyle> headerStyles = new TreeMap<String, HeaderStyle>();*/
    private final HeaderStyles headerStyles = new HeaderStyles();

    /*private boolean useDefaultDocumentTypes = true;
    private SortedMap<DocumentType, HeaderStyle> documentTypes = new TreeMap<DocumentType, HeaderStyle>();*/
    private final DocumentTypes documentTypes = new DocumentTypes(headerStyles);

    private boolean verbose = false;
    private boolean quiet = false;
    private boolean dryRun = false;
    private boolean failOnError = true;
    private int threadNumber = 1;
    private String encoding = System.getProperty("file.encoding");


}

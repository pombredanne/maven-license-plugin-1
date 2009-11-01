package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class LicenseManager {

    /*private boolean verbose = false;
    private boolean quiet = false;
    private boolean dryRun = false;
    private boolean failOnError = true;
    private int threadNumber = 1;
    private String encoding = System.getProperty("file.encoding");*/
    private final Settings settings = new SettingsImpl();

    /*private File baseDirectory = new File(".");
    private Set<String> includes = new HashSet<String>();
    private Set<String> excludes = new HashSet<String>();
    private boolean withDefaultExcludes = true;*/
    private final FileSelector fileSelection = new FileSelector();

    /*private URL header;
    private Map<String, Object> headerPlaceholders = new HashMap<String, Object>();*/
    private final LicenseHeader licenseHeader = new LicenseHeaderImpl(settings, getClass().getResource(""));

    /*private Pattern headerDetectionPattern = Pattern.compile("(?is).*copyright.*");*/
    private final HeaderDetector headerDetector = new HeaderDetector();

    /*private boolean useDefaultHeaderStyles = true;
    private SortedMap<String, HeaderStyle> headerStyles = new TreeMap<String, HeaderStyle>();*/
    private final HeaderStyles headerStyles = new HeaderStyles();

    /*private boolean useDefaultDocumentTypes = true;
    private SortedMap<DocumentType, HeaderStyle> documentTypes = new TreeMap<DocumentType, HeaderStyle>();*/
    private final DocumentTypes documentTypes = new DocumentTypes(headerStyles);

}

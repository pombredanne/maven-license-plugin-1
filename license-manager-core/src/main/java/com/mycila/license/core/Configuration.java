package com.mycila.license.core;

import com.mycila.license.core.style.HeaderStyles;
import com.mycila.license.core.type.DocumentTypes;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class Configuration {

    private final FileSelection fileSelection = new FileSelection();

    private final LicenseHeader licenseHeader = new LicenseHeader();

    private final HeaderStyles headerStyles = new HeaderStyles();
    /*private boolean useDefaultHeaderStyles = true;
    private SortedMap<String, HeaderStyle> headerStyles = new TreeMap<String, HeaderStyle>();*/

    private final DocumentTypes documentTypes = new DocumentTypes();

    private boolean verbose = false;
    private boolean quiet = false;
    private boolean dryRun = false;
    private boolean failOnError = true;
    private int threadNumber = 1;
    private String encoding = System.getProperty("file.encoding");

    
}

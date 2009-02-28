package com.mycila.license.core;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class LicenseHeader {
    private URL header;
    private Pattern headerDetectionPattern = Pattern.compile("(?is).*copyright.*");
    private Map<String, Object> headerPlaceholders = new HashMap<String, Object>();
}

package com.mycila.license.core;

import static com.mycila.license.core.Check.*;

import java.util.regex.Pattern;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class HeaderDetector {

    private static final String DEFAULT_PATTERN = "(?is).*copyright.*";

    private final Pattern pattern;

    HeaderDetector() {
        this(DEFAULT_PATTERN);
    }

    HeaderDetector(String pattern) {
        notNull(pattern, "header detection pattern");
        this.pattern = Pattern.compile(pattern);
    }

    String getPattern() {
        return pattern.pattern();
    }

}

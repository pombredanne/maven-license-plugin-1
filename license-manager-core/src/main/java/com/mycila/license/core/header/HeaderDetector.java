package com.mycila.license.core.header;

import static com.mycila.license.core.util.Check.*;

import java.util.regex.Pattern;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class HeaderDetector {

    private static final String DEFAULT_PATTERN = "(?is).*copyright.*";

    private final Pattern pattern;

    private HeaderDetector(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern.pattern();
    }

    public static HeaderDetector newHeaderDetector() {
        return newHeaderDetector(DEFAULT_PATTERN);
    }

    public static HeaderDetector newHeaderDetector(String pattern) {
        notNull(pattern, "header detection pattern");
        return new HeaderDetector(Pattern.compile(pattern));
    }
}

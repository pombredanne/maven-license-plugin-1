package com.mycila.license.core;

import static com.mycila.license.core.Check.*;

import java.io.File;
import static java.util.Arrays.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class FileSelector {

    private File baseDirectory = new File(".");
    private Set<String> includes = new HashSet<String>();
    private Set<String> excludes = new HashSet<String>();
    private boolean useDefaultExcludes = true;

    FileSelector() {}

    FileSelector changeBaseDirectory(File baseDirectory) {
        notNull(baseDirectory, "Base directory");
        this.baseDirectory = baseDirectory;
        return this;
    }

    FileSelector withDefaultExcludes() {
        useDefaultExcludes = true;
        return this;
    }

    FileSelector withoutDefaultExcludes() {
        useDefaultExcludes = false;
        return this;
    }

    FileSelector include(String... includes) {
        notNull(includes, "Includes");
        this.includes.addAll(asList(includes));
        return this;
    }

    FileSelector exclude(String... excludes) {
        notNull(excludes, "Excludes");
        this.excludes.addAll(asList(excludes));
        return this;
    }

    FileSelectionImpl select() {
        return new FileSelectionImpl(baseDirectory, useDefaultExcludes, includes, excludes);
    }

}

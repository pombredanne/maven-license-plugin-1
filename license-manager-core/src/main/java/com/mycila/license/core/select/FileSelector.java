package com.mycila.license.core.select;

import static com.mycila.license.core.util.Check.*;

import java.io.File;
import static java.util.Arrays.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class FileSelector {

    private File baseDirectory = new File(".");
    private Set<String> includes = new HashSet<String>();
    private Set<String> excludes = new HashSet<String>();
    private boolean useDefaultExcludes = true;

    private FileSelector() {}

    public FileSelector changeBaseDirectory(File baseDirectory) {
        notNull(baseDirectory, "Base directory");
        this.baseDirectory = baseDirectory;
        return this;
    }

    public FileSelector withDefaultExcludes() {
        useDefaultExcludes = true;
        return this;
    }

    public FileSelector withoutDefaultExcludes() {
        useDefaultExcludes = false;
        return this;
    }

    public FileSelector include(String... includes) {
        notNull(includes, "Includes");
        this.includes.addAll(asList(includes));
        return this;
    }

    public FileSelector exclude(String... excludes) {
        notNull(excludes, "Excludes");
        this.excludes.addAll(asList(excludes));
        return this;
    }

    public FileSelection select() {
        return new FileSelection(baseDirectory, useDefaultExcludes, includes, excludes);
    }

    public static FileSelector newFileSelector() {
        return new FileSelector();
    }
}

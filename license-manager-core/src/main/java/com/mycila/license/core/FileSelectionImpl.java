package com.mycila.license.core;

import org.codehaus.plexus.util.DirectoryScanner;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class FileSelectionImpl implements FileSelection {

    private final File baseDirectory;
    private final boolean useDefaultExcludes;
    private final Set<String> includes;
    private final Set<String> excludes;
    private final Set<File> selected = new HashSet<File>();

    FileSelectionImpl(File baseDirectory, boolean useDefaultExcludes, Set<String> includes, Set<String> excludes) {
        this.baseDirectory = baseDirectory;
        this.useDefaultExcludes = useDefaultExcludes;
        this.includes = Collections.unmodifiableSet(new HashSet<String>(includes));
        this.excludes = Collections.unmodifiableSet(excludes);
        scan();
    }

    private void scan() {
        HashSet<String> includes = new HashSet<String>(getIncludes());
        HashSet<String> excludes = new HashSet<String>(getExcludes());
        if (includes.isEmpty()) {
            includes.add(DEFAULT_INCLUDE);
        }
        if (isUseDefaultExcludes()) {
            excludes.addAll(DEFAULT_EXCLUDES);
        }
        DirectoryScanner directoryScanner = new DirectoryScanner();
        directoryScanner.setBasedir(getBaseDirectory());
        directoryScanner.setIncludes(includes.toArray(new String[includes.size()]));
        directoryScanner.setExcludes(excludes.toArray(new String[excludes.size()]));
        directoryScanner.scan();
        for (String file : directoryScanner.getIncludedFiles()) {
            selected.add(new File(file));
        }
    }

    public File getBaseDirectory() {
        return baseDirectory;
    }
    public Set<String> getIncludes() {
        return includes;
    }
    public Set<File> getSelectedFile() {
        return selected;
    }
    public Set<String> getExcludes() {
        return excludes;
    }
    public boolean isUseDefaultExcludes() {
        return useDefaultExcludes;
    }
}

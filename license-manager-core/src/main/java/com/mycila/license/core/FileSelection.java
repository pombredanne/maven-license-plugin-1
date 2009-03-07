package com.mycila.license.core;

import org.codehaus.plexus.util.DirectoryScanner;

import java.io.File;
import static java.util.Arrays.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class FileSelection {

    private static final List<String> DEFAULT_EXCLUDES = asList(
            "**/*~", "**/#*#", "**/.#*", "**/%*%", "**/._*",
            "**/CVS", "**/CVS/**", "**/.cvsignore",
            "**/SCCS", "**/SCCS/**", "**/vssver.scc",
            "**/.svn", "**/.svn/**",
            "**/.arch-ids", "**/.arch-ids/**",
            "**/.bzr", "**/.bzr/**",
            "**/.MySCMServerInfo", "**/.DS_Store",
            "**/cobertura.ser", "**/.clover/**",
            "**/.classpath", "**/.project", "**/.settings/**",
            "**/*.iml", "**/*.ipr", "**/*.iws",
            "**/*.jpg", "**/*.png", "**/*.gif", "**/*.ico", "**/*.bmp",
            "**/*.class", "**/*.exe", "**/MANIFEST.MF",
            "**/*.jar", "**/*.zip", "**/*.rar", "**/*.tar", "**/*.tar.gz", "**/*.tar.bz2"
    );

    private static final String DEFAULT_INCLUDE = "**";

    private final File baseDirectory;
    private final boolean useDefaultExcludes;
    private final Set<String> includes;
    private final Set<String> excludes;
    private final Set<File> selected = new HashSet<File>();

    FileSelection(File baseDirectory, boolean useDefaultExcludes, Set<String> includes, Set<String> excludes) {
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

    File getBaseDirectory() {
        return baseDirectory;
    }

    Set<String> getIncludes() {
        return includes;
    }

    Set<File> getSelectedFile() {
        return selected;
    }

    Set<String> getExcludes() {
        return excludes;
    }

    boolean isUseDefaultExcludes() {
        return useDefaultExcludes;
    }

}

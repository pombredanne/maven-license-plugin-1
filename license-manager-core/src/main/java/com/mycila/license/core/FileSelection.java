package com.mycila.license.core;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class FileSelection {

    private static final String[] DEFAULT_EXCLUDES = {
            "**/*~", "**/#*#", "**/.#*", "**/%*%", "**/._*",
            "**/CVS", "**/CVS/**", "**/.cvsignore",
            "**/SCCS", "**/SCCS/**",  "**/vssver.scc",
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
    };

    private File baseDirectory = new File(".");
    private Set<String> includes = new HashSet<String>();
    private Set<String> excludes = new HashSet<String>();
    private boolean useDefaultExcludes = true;

}

package com.mycila.license.core;

import java.io.File;
import static java.util.Arrays.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
interface FileSelection {

    String DEFAULT_INCLUDE = "**";
    Set<String> DEFAULT_EXCLUDES = Collections.unmodifiableSet(new HashSet<String>(asList(
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
    )));

    File getBaseDirectory();
    Set<String> getIncludes();
    Set<File> getSelectedFile();
    Set<String> getExcludes();
    boolean isUseDefaultExcludes();
}

package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
interface Settings {
    boolean isVerbose();
    boolean isQuiet();
    boolean isDryRun();
    boolean isFailOnError();
    int getThreadNumber();
    String getEncoding();
}

package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class Settings {
    private boolean verbose = false;
    private boolean quiet = false;
    private boolean dryRun = false;
    private boolean failOnError = true;
    private int threadNumber = 1;
    private String encoding = System.getProperty("file.encoding");
    
    boolean isVerbose() {
        return verbose;
    }

    boolean isQuiet() {
        return quiet;
    }

    boolean isDryRun() {
        return dryRun;
    }

    boolean isFailOnError() {
        return failOnError;
    }

    int getThreadNumber() {
        return threadNumber;
    }

    String getEncoding() {
        return encoding;
    }
}

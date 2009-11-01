package com.mycila.license.core;

import static com.mycila.license.core.Check.*;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class SettingsImpl implements Settings {

    private boolean verbose = false;
    private boolean quiet = false;
    private boolean dryRun = false;
    private boolean failOnError = true;
    private int threadNumber = 1;
    private String encoding = System.getProperty("file.encoding");
    
    public boolean isVerbose() {
        return verbose;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public boolean isDryRun() {
        return dryRun;
    }

    public boolean isFailOnError() {
        return failOnError;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public String getEncoding() {
        return encoding;
    }

    Settings setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    Settings setQuiet(boolean quiet) {
        this.quiet = quiet;
        return this;
    }

    Settings setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
        return this;
    }

    Settings setFailOnError(boolean failOnError) {
        this.failOnError = failOnError;
        return this;
    }

    Settings setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
        return this;
    }

    Settings setEncoding(String encoding) {
        notNull(encoding, "Default encoding");
        this.encoding = encoding;
        return this;

    }

}

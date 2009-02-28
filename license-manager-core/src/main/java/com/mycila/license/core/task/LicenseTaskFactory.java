package com.mycila.license.core.task;

import java.util.Set;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public interface LicenseTaskFactory {
    Set<Task> checkLicenseHeader();
    Set<Task> removeAnyLicenseHeader();
    Set<Task> updateLicenseHeader();
}

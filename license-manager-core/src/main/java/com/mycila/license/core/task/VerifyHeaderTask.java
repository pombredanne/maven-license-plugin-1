package com.mycila.license.core.task;

import com.mycila.license.core.style.HeaderStyle;

import java.io.File;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public interface VerifyHeaderTask extends Task<Boolean> {
    HeaderStyle getHeader();
    File getFile();
}

package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
interface DocumentType {
    String getExtension();
    HeaderStyle getHeaderStyle();
}

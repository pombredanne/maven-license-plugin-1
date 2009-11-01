package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
interface Document {
    DocumentType getDocumentType();
    EOL getEOL();
}

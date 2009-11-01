package com.mycila.license.core;

import java.io.File;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class DocumentImpl implements Document {

    private File location;

    DocumentImpl(File location) {
        this.location = location;
    }

    public DocumentType getDocumentType() {
        //TODO
        return null;
    }

    public EOL getEOL() {
        //TODO
        return null;
    }

}

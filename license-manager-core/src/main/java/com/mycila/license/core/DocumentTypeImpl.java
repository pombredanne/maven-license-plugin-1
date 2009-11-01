package com.mycila.license.core;

import static com.mycila.license.core.Check.*;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class DocumentTypeImpl implements Comparable<DocumentType>, DocumentType {

    private final String extension;
    HeaderStyle headerStyle;

    DocumentTypeImpl(String extension) {
        notNull(extension, "Document extension");
        this.extension = extension.toLowerCase();
    }

    public String getExtension() {
        return extension;
    }

    public HeaderStyle getHeaderStyle() {
        return headerStyle;
    }

    public int compareTo(DocumentType o) {
        return getExtension().compareTo(o.getExtension());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !DocumentType.class.isAssignableFrom(o.getClass())) return false;
        DocumentType that = (DocumentType) o;
        return getExtension().equals(that.getExtension());
    }

    @Override
    public int hashCode() {
        return extension.hashCode();
    }

    @Override
    public String toString() {
        return "DocumentType: " + getExtension() + " => " + getHeaderStyle();
    }
}

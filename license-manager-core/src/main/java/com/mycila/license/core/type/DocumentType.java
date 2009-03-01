package com.mycila.license.core.type;

import com.mycila.license.core.style.HeaderStyle;
import static com.mycila.license.core.util.Check.*;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class DocumentType implements Comparable<DocumentType> {

    private final String extension;
    HeaderStyle headerStyle;

    DocumentType(String extension) {
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
        if (o == null || getClass() != o.getClass()) return false;
        DocumentType that = (DocumentType) o;
        return extension.equals(that.extension);
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

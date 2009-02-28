package com.mycila.license.core.type;

import com.mycila.license.core.style.HeaderStyle;
import static com.mycila.license.core.util.Check.*;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class DocumentType implements Comparable<DocumentType> {

    private final String extension;
    private final HeaderStyle headerStyle;

    DocumentType(String extension, HeaderStyle headerStyle) {
        notNull(extension, "Document extension");
        notNull(extension, "Header style");
        this.extension = extension.toLowerCase();
        this.headerStyle = headerStyle;
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
}

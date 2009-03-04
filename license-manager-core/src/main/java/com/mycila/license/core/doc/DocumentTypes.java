package com.mycila.license.core.doc;

import com.mycila.license.core.Configuration;
import com.mycila.license.core.header.HeaderStyle;
import com.mycila.license.core.header.HeaderStyles;
import static com.mycila.license.core.util.Check.*;
import com.mycila.xmltool.CallBack;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;
import com.mycila.xmltool.util.ValidationResult;

import java.net.URL;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentTypes {

    private static final URL DEFAULT_DOCUMENT_TYPES = Configuration.class.getResource("/com/mycila/license/core/doc/default-document-types.xml");
    private static final URL DOCUMENT_TYPES_SCHEMA = Configuration.class.getResource("/com/mycila/license/core/doc/document-types.xsd");

    private final SortedSet<DocumentType> documentTypes = new TreeSet<DocumentType>();
    private final HeaderStyles headerStyles;

    private DocumentTypes(HeaderStyles headerStyles) {
        this.headerStyles = headerStyles;
    }

    public DocumentTypes loadDefaults() {
        return add(DEFAULT_DOCUMENT_TYPES);
    }

    public DocumentTypes add(URL mappingLocation) {
        notNull(mappingLocation, "Mapping location");
        XMLTag tag = XMLDoc.from(mappingLocation, false);
        ValidationResult res = tag.validate(DOCUMENT_TYPES_SCHEMA);
        if (res.hasError()) {
            throw new IllegalArgumentException("Mapping definition at '" + mappingLocation + "' is not valid: " + res.getErrorMessages()[0]);
        }
        tag.forEachChild(new CallBack() {
            public void execute(XMLTag doc) {
                map(doc.getAttribute("extension")).to(headerStyles.getByName(doc.getAttribute("style")));
            }
        });
        return this;
    }

    public SortedSet<DocumentType> getDocumentTypes() {
        return Collections.unmodifiableSortedSet(documentTypes);
    }

    public MappingBuilder map(String extension) {
        notNull(extension, "Document extension");
        final DocumentType type = new DocumentType(extension.toLowerCase());
        documentTypes.remove(type);
        documentTypes.add(type);
        return new MappingBuilder() {
            public DocumentTypes to(HeaderStyle headerStyle) {
                notNull(headerStyle, "Header style");
                type.headerStyle = headerStyle;
                return DocumentTypes.this;
            }
        };
    }

    public boolean isExtensionSupported(String extension) {
        if (extension != null) {
            for (DocumentType documentType : documentTypes) {
                if (documentType.getExtension().equalsIgnoreCase(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    public DocumentType getByExtension(String extension) {
        notNull(extension, "Document extension");
        for (DocumentType documentType : documentTypes) {
            if (documentType.getExtension().equalsIgnoreCase(extension)) {
                return documentType;
            }
        }
        throw new IllegalStateException("Unsupported document type: " + extension);
    }

    public int size() {
        return documentTypes.size();
    }

    static interface MappingBuilder {
        DocumentTypes to(HeaderStyle headerStyle);
    }

    public static DocumentTypes newDocumentTypes(HeaderStyles headerStyles) {
        notNull(headerStyles, "Header styles");
        return new DocumentTypes(headerStyles);
    }
}

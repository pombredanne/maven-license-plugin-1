package com.mycila.license.core;

import static com.mycila.license.core.Check.*;
import com.mycila.xmltool.CallBack;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;
import com.mycila.xmltool.util.ValidationResult;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class DocumentTypes implements Iterable<DocumentType> {

    private static final URL DEFAULT_DOCUMENT_TYPES = LicenseManager.class.getResource("/com/mycila/license/core/default-document-types.xml");
    private static final URL DOCUMENT_TYPES_SCHEMA = LicenseManager.class.getResource("/com/mycila/license/core/document-types.xsd");

    private final SortedSet<DocumentType> documentTypes = new TreeSet<DocumentType>();
    private final HeaderStyles headerStyles;

    DocumentTypes(HeaderStyles headerStyles) {
        notNull(headerStyles, "Header styles");
        this.headerStyles = headerStyles;
    }

    public Iterator<DocumentType> iterator() {
        return documentTypes.iterator();
    }

    DocumentTypes loadDefaults() {
        return add(DEFAULT_DOCUMENT_TYPES);
    }

    DocumentTypes add(URL mappingLocation) {
        notNull(mappingLocation, "Mapping location");
        XMLTag tag = XMLDoc.from(mappingLocation, false);
        ValidationResult res = tag.validate(DOCUMENT_TYPES_SCHEMA);
        if (res.hasError()) {
            throw new LicenseManagerException("Mapping definition at '" + mappingLocation + "' is not valid: " + res.getErrorMessages()[0]);
        }
        tag.forEachChild(new CallBack() {
            public void execute(XMLTag doc) {
                map(doc.getAttribute("extension")).to(headerStyles.getByName(doc.getAttribute("style")));
            }
        });
        return this;
    }

    SortedSet<DocumentType> getDocumentTypes() {
        return Collections.unmodifiableSortedSet(documentTypes);
    }

    MappingBuilder map(String extension) {
        notNull(extension, "Document extension");
        final DocumentTypeImpl type = new DocumentTypeImpl(extension.toLowerCase());
        documentTypes.remove(type);
        documentTypes.add(type);
        return new MappingBuilder() {
            public DocumentTypes to(HeaderStyleImpl headerStyle) {
                notNull(headerStyle, "Header style");
                type.headerStyle = headerStyle;
                return DocumentTypes.this;
            }
        };
    }

    boolean isSupported(File file) {
        if(file == null) {
            return false;
        }
        int pos = file.getName().lastIndexOf(".");
        String ext = pos == -1 ? "" : file.getName().substring(pos + 1);
        return isExtensionSupported(ext);
    }

    boolean isExtensionSupported(String extension) {
        if (extension != null) {
            for (DocumentTypeImpl documentType : documentTypes) {
                if (documentType.getExtension().equalsIgnoreCase(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    DocumentTypeImpl getByExtension(String extension) {
        notNull(extension, "Document extension");
        for (DocumentTypeImpl documentType : documentTypes) {
            if (documentType.getExtension().equalsIgnoreCase(extension)) {
                return documentType;
            }
        }
        throw new LicenseManagerException("Unsupported document type: " + extension);
    }

    int size() {
        return documentTypes.size();
    }

    static interface MappingBuilder {
        DocumentTypes to(HeaderStyleImpl headerStyle);
    }
}

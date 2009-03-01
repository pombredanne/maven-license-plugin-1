package com.mycila.license.core.type;

import com.google.code.xmltool.CallBack;
import com.google.code.xmltool.XMLDoc;
import com.google.code.xmltool.XMLTag;
import com.google.code.xmltool.util.ValidationResult;
import com.mycila.license.core.Configuration;
import com.mycila.license.core.style.HeaderStyle;
import com.mycila.license.core.style.HeaderStyles;
import static com.mycila.license.core.util.Check.*;

import java.net.URL;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentTypes {

    private static final URL DEFAULT_DOCUMENT_TYPES = Configuration.class.getResource("/com/mycila/license/core/type/default-document-types.xml");
    private static final URL DOCUMENT_TYPES_SCHEMA = Configuration.class.getResource("/com/mycila/license/core/type/document-types.xsd");

    private final SortedSet<DocumentType> documentTypes = new TreeSet<DocumentType>();
    private final HeaderStyles headerStyles;

    public DocumentTypes(HeaderStyles headerStyles) {
        notNull(headerStyles, "Header styles");
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
        DocumentType type = new DocumentType(extension.toLowerCase());
        documentTypes.add(type);
        return MappingBuilder.of(type);
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

    static class MappingBuilder {

        private final DocumentType documentType;

        MappingBuilder(DocumentType documentType) {
            this.documentType = documentType;
        }

        DocumentType to(HeaderStyle headerStyle) {
            notNull(headerStyle, "Header style");
            documentType.headerStyle = headerStyle;
            return documentType;
        }

        static MappingBuilder of(DocumentType documentType) {
            return new MappingBuilder(documentType);
        }
    }

}

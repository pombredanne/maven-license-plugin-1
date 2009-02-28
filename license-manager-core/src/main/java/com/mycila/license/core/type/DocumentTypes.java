package com.mycila.license.core.type;

import com.mycila.license.core.Configuration;
import com.mycila.license.core.style.HeaderStyle;

import java.net.URL;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class DocumentTypes {

    private static final URL DEFAULT_DOCUMENT_TYPES = Configuration.class.getResource("/com/mycila/license/core/type/default-document-types.xml");

    private boolean useDefaultDocumentTypes = true;
    private SortedMap<DocumentType, HeaderStyle> documentTypes = new TreeMap<DocumentType, HeaderStyle>();
    
}

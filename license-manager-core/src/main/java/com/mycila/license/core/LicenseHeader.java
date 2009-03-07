package com.mycila.license.core;

import static com.mycila.license.core.Check.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class LicenseHeader {

    private final URL headerLocation;
    private final Map<String, Object> headerPlaceholders = new HashMap<String, Object>();
    private final List<String> headerLines = new ArrayList<String>();

    private LicenseHeader(Settings settings, URL headerLocation) {
        this.headerLocation = headerLocation;
        // check the header location. If it is on the filesystem, load it with the good encoding.
        // Otherwise, get the stream and reads using provided encoding.
        try {
            File file = new File(headerLocation.toURI());
            BufferedReader reader = new BufferedReader(file.exists() ?
                    new FileReader(file) :
                    new InputStreamReader(headerLocation.openStream(), settings.getEncoding()));
            String line;
            while ((line = reader.readLine()) != null) {
                headerLines.add(line);
            }
            reader.close();
        } catch (Exception e) {
            throw new LicenseManagerException("Error reading header from " + headerLocation + ": " + e.getMessage(), e);
        }
    }

    URL getHeaderLocation() {
        return headerLocation;
    }

    PlaceHolderBuilder replace(final String placeHolder) {
        return new PlaceHolderBuilder() {
            public LicenseHeader by(Object object) {
                LicenseHeader.this.headerPlaceholders.put(placeHolder, object);
                return LicenseHeader.this;
            }
        };
    }

    static LicenseHeader newLicenseHeader(Settings settings, URL headerLocation) {
        notNull(headerLocation, "Header location");
        return new LicenseHeader(settings, headerLocation);
    }

    interface PlaceHolderBuilder {
        LicenseHeader by(Object object);
    }
}

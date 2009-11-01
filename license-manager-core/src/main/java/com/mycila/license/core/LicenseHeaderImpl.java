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
final class LicenseHeaderImpl implements LicenseHeader {

    private final URL headerLocation;
    private final Map<String, Object> headerPlaceholders = new HashMap<String, Object>();
    private final List<String> headerLines = new ArrayList<String>();

    LicenseHeaderImpl(Settings settings, URL headerLocation) {
        notNull(settings, "License Manager Settings");
        notNull(headerLocation, "Header location");
        this.headerLocation = headerLocation;
        // check the header location. If it is on the filesystem, load it with the good encoding.
        // Otherwise, get the stream and reads using provided encoding.
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(headerLocation.toURI())));
        }
        catch (Exception e) {
            try {
                reader = new BufferedReader(new InputStreamReader(headerLocation.openStream(), settings.getEncoding()));
            } catch (Exception e1) {
                throw new LicenseManagerException("Error reading header from " + headerLocation + ": " + e.getMessage(), e);
            }
        }
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                headerLines.add(line);
            }
            reader.close();
        } catch (Exception e) {
            throw new LicenseManagerException("Error reading header from " + headerLocation + ": " + e.getMessage(), e);
        }
    }

    public URL getHeaderLocation() {
        return headerLocation;
    }

    PlaceHolderBuilder replace(final String placeHolder) {
        notNull(placeHolder, "Placeholder name");
        return new PlaceHolderBuilder() {
            public LicenseHeaderImpl by(Object object) {
                notNull(object, "Placeholder value");
                LicenseHeaderImpl.this.headerPlaceholders.put(placeHolder, object);
                return LicenseHeaderImpl.this;
            }
        };
    }

    List<String> getLines() {
        List<String> lines = new ArrayList<String>(headerLines.size());
        for (String headerLine : headerLines) {

        }
        return null;
    }

    String getContent(EOL eol) {
        return null;
    }

    interface PlaceHolderBuilder {
        LicenseHeaderImpl by(Object object);
    }
}

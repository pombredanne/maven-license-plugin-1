package com.mycila.license.core.header;

import static com.mycila.license.core.util.Check.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class LicenseHeader {

    private final URL headerLocation;
    private final Map<String, Object> headerPlaceholders = new HashMap<String, Object>();

    private LicenseHeader(URL headerLocation) {
        this.headerLocation = headerLocation;
    }

    public URL getHeaderLocation() {
        return headerLocation;
    }

    public PlaceHolderBuilder replace(final String placeHolder) {
        return new PlaceHolderBuilder() {
            public LicenseHeader with(Object object) {
                LicenseHeader.this.headerPlaceholders.put(placeHolder, object);
                return LicenseHeader.this;
            }
        };
    }

    public static LicenseHeader newLicenseHeader(URL headerLocation) {
        notNull(headerLocation, "Header location");
        return new LicenseHeader(headerLocation);
    }

    public interface PlaceHolderBuilder {
        LicenseHeader with(Object object);
    }
}

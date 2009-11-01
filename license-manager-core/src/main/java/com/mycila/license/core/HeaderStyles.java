package com.mycila.license.core;

import static com.mycila.license.core.Check.*;
import com.mycila.xmltool.CallBack;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;
import com.mycila.xmltool.util.ValidationResult;

import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class HeaderStyles implements Iterable<HeaderStyle> {

    private static final URL DEFAULT_HEADER_STYLES = LicenseManager.class.getResource("/com/mycila/license/core/default-styles.xml");
    private static final URL HEADER_STYLES_SCHEMA = LicenseManager.class.getResource("/com/mycila/license/core/header-style.xsd");

    private final SortedSet<HeaderStyle> headerStyles = new TreeSet<HeaderStyle>();

    HeaderStyles() {}

    public Iterator<HeaderStyle> iterator() {
        final Iterator<HeaderStyle> it = headerStyles.iterator();
        return new Iterator<HeaderStyle>() {
            public boolean hasNext() {
                return it.hasNext();
            }
            public HeaderStyle next() {
                return it.next();
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    HeaderStyles loadDefaults() {
        return add(DEFAULT_HEADER_STYLES);
    }

    HeaderStyles add(URL styleLocation) {
        notNull(styleLocation, "Style location");
        XMLTag tag = XMLDoc.from(styleLocation, false);
        ValidationResult res = tag.validate(HEADER_STYLES_SCHEMA);
        if (res.hasError()) {
            throw new LicenseManagerException("Style definition at '" + styleLocation + "' is not valid: " + res.getErrorMessages()[0]);
        }
        final String ns = tag.getPefix("http://mycila.com/license/styles/1.0");
        tag.forEachChild(new CallBack() {
            public void execute(XMLTag doc) {
                Builder builder = add(doc.getAttribute("name"))
                        .defineBegining(doc.getTextOrCDATA("%1$s:definition/%1$s:begining", ns))
                        .defineStartLine(doc.getTextOrCDATA("%1$s:definition/%1$s:startLine", ns))
                        .defineEnding(doc.getTextOrCDATA("%1$s:definition/%1$s:ending", ns))
                        .detectBegining(doc.getTextOrCDATA("%1$s:detection/%1$s:begining", ns))
                        .detectEnding(doc.getTextOrCDATA("%1$s:detection/%1$s:ending", ns));
                if (doc.hasTag("%1$s:description", ns)) {
                    builder.description(doc.getTextOrCDATA("%1$s:description", ns));
                }
                if (doc.hasTag("%1$s:detection/%1$s:skip", ns)) {
                    builder.detectSkip(doc.getTextOrCDATA("%1$s:detection/%1$s:skip", ns));
                }
            }
        });
        return this;
    }

    Builder add(String styleName) {
        notNull(styleName, "Style name");
        HeaderStyleImpl style = new HeaderStyleImpl(styleName.toLowerCase());
        headerStyles.remove(style);
        headerStyles.add(style);
        return Builder.of(style);
    }

    HeaderStyle getByName(String name) {
        notNull(name, "Style name");
        name = name.toLowerCase();
        for (HeaderStyle headerStyle : headerStyles) {
            if (headerStyle.getName().equals(name)) {
                return headerStyle;
            }
        }
        throw new LicenseManagerException("Inexisting Header Style: " + name);
    }

    SortedSet<HeaderStyle> getHeaderStyles() {
        return Collections.unmodifiableSortedSet(headerStyles);
    }

    int size() {
        return headerStyles.size();
    }

    static class Builder {

        private final HeaderStyleImpl headerStyle;

        Builder(HeaderStyleImpl headerStyle) {
            this.headerStyle = headerStyle;
        }

        HeaderStyle build() {
            notNull(headerStyle.defineBegining, "Begining definition");
            notNull(headerStyle.defineStartLine, "StartLine definition");
            notNull(headerStyle.defineEnding, "Ending definition");
            notNull(headerStyle.detectBegining, "Begining detection");
            notNull(headerStyle.detectEnding, "Ending detection");
            return headerStyle;
        }

        static Builder of(HeaderStyleImpl headerStyle) {
            return new Builder(headerStyle);
        }

        Builder defineBegining(String begining) {
            headerStyle.defineBegining = begining;
            return this;
        }

        Builder defineStartLine(String startLine) {
            headerStyle.defineStartLine = startLine;
            return this;
        }

        Builder defineEnding(String ending) {
            headerStyle.defineEnding = ending;
            return this;
        }

        Builder detectSkip(String skip) {
            headerStyle.detectSkip = skip;
            return this;
        }

        Builder detectBegining(String begining) {
            headerStyle.detectBegining = begining;
            return this;
        }

        Builder detectEnding(String ending) {
            headerStyle.detectEnding = ending;
            return this;
        }

        Builder description(String description) {
            headerStyle.description = description;
            return this;
        }
    }

}

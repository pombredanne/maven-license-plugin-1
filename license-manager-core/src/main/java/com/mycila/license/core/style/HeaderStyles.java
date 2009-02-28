package com.mycila.license.core.style;

import com.google.code.xmltool.CallBack;
import com.google.code.xmltool.XMLDoc;
import com.google.code.xmltool.XMLTag;
import com.google.code.xmltool.util.ValidationResult;
import com.mycila.license.core.Configuration;
import static com.mycila.license.core.util.Check.*;

import java.net.URL;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class HeaderStyles {

    private static final URL DEFAULT_HEADER_STYLES = Configuration.class.getResource("/com/mycila/license/core/style/default-styles.xml");
    private static final URL HEADER_STYLES_SCHEMA = Configuration.class.getResource("/com/mycila/license/core/style/header-style.xsd");

    private SortedSet<HeaderStyle> headerStyles = new TreeSet<HeaderStyle>();

    public HeaderStyles loadDefaults() {
        return add(DEFAULT_HEADER_STYLES);
    }

    public HeaderStyles add(URL styleLocation) {
        notNull(styleLocation, "Style location");
        XMLTag tag = XMLDoc.from(styleLocation, false);
        ValidationResult res = tag.validate(HEADER_STYLES_SCHEMA);
        if(res.hasError()) {
            throw new IllegalArgumentException("Style definition file at '" + styleLocation + "' is not valid: " + res.getErrorMessages()[0]);
        }
        final String ns = tag.getPefix("http://mycila.com/license/styles/1.0");
        tag.forEachChild(new CallBack() {
            public void execute(XMLTag doc) {
                add(doc.getAttribute("name"))
                        .description(doc.rawXpathString("%1$s:definition/%1$s:begining/text()", ns))
                        .defineBegining(doc.getText("%1$s:definition/%1$s:begining", ns))
                        .defineStartLine(doc.getText("%1$s:definition/%1$s:startLine", ns))
                        .defineEnding(doc.getText("%1$s:definition/%1$s:ending", ns))
                        .detectSkip(doc.rawXpathString("%1$s:detection/%1$s:skip/text()", ns))
                        .detectBegining(doc.getText("%1$s:detection/%1$s:begining", ns))
                        .detectEnding(doc.getText("%1$s:detection/%1$s:ending", ns))
                        .build();
            }
        });
        return this;
    }

    public Builder add(String styleName) {
        notNull(styleName, "Style name");
        HeaderStyle style = new HeaderStyle(styleName.toLowerCase());
        headerStyles.add(style);
        return Builder.of(style);
    }

    public HeaderStyle getHeaderStyle(String name) {
        notNull(name, "Style name");
        for (HeaderStyle headerStyle : headerStyles) {
            if(headerStyle.getName().equalsIgnoreCase(name)) {
                return headerStyle;
            }
        }
        throw new IllegalStateException("Inexisting Header Style: " + name);
    }

    public SortedSet<HeaderStyle> getHeaderStyles() {
        return Collections.unmodifiableSortedSet(headerStyles);
    }

    public int size() {
        return headerStyles.size();
    }

    static class Builder {

        private final HeaderStyle headerStyle;

        Builder(HeaderStyle headerStyle) {
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

        static Builder of(HeaderStyle headerStyle) {
            return new Builder(headerStyle);
        }

        public Builder defineBegining(String begining) {
            headerStyle.defineBegining = begining;
            return this;
        }

        public Builder defineStartLine(String startLine) {
            headerStyle.defineStartLine = startLine;
            return this;
        }

        public Builder defineEnding(String ending) {
            headerStyle.defineEnding = ending;
            return this;
        }

        public Builder detectSkip(String skip) {
            headerStyle.detectSkip = skip;
            return this;
        }

        public Builder detectBegining(String begining) {
            headerStyle.detectBegining = begining;
            return this;
        }

        public Builder detectEnding(String ending) {
            headerStyle.detectEnding = ending;
            return this;
        }

        public Builder description(String description) {
            headerStyle.description = description;
            return this;
        }
    }
}

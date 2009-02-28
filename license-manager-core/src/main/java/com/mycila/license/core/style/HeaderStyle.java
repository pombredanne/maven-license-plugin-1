package com.mycila.license.core.style;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class HeaderStyle implements Comparable<HeaderStyle> {

    private final String name;
    String description;
    String defineBegining;
    String defineStartLine;
    String defineEnding;
    String detectSkip;
    String detectBegining;
    String detectEnding;

    HeaderStyle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderStyle that = (HeaderStyle) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HeaderStyle '").append(name).append("'");
        if (description != null) {
            sb.append(" (").append(description).append(")");
        }
        return sb
                .append("\n")
                .append(" - begining: ").append(defineBegining).append("\n")
                .append(" - before each line: ").append(defineStartLine).append("\n")
                .append(" - ending: ").append(defineEnding)
                .toString();
    }

    public int compareTo(HeaderStyle o) {
        return getName().compareTo(o.getName());
    }
}

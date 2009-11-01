package com.mycila.license.core;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class HeaderStyleImpl implements Comparable<HeaderStyle>, HeaderStyle {

    private final String name;
    String description;
    String defineBegining;
    String defineStartLine;
    String defineEnding;
    String detectSkip;
    String detectBegining;
    String detectEnding;

    HeaderStyleImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderStyleImpl that = (HeaderStyleImpl) o;
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
                .append(" - ending: ").append(defineEnding).append("\n")
                .append(" - detection to skip with: ").append(detectSkip).append("\n")
                .append(" - detection of begining with: ").append(detectBegining).append("\n")
                .append(" - detection of end with: ").append(detectEnding)
                .toString();
    }

    public int compareTo(HeaderStyle o) {
        return getName().compareTo(o.getName());
    }
}

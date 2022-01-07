package District;

import java.util.Objects;

public class District {
    private final String title;
    private String description;

    public District(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public District(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        District district = (District) o;
        return Objects.equals(title, district.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return title + ": " + description;
    }
}

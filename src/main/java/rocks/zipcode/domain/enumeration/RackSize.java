package rocks.zipcode.domain.enumeration;

/**
 * The RackSize enumeration.
 */
public enum RackSize {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large"),
    XL("Extra-Large");

    private final String value;

    RackSize(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

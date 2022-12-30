package rocks.zipcode.domain.enumeration;

/**
 * The StarRating enumeration.
 */
public enum StarRating {
    ONE("One"),
    TWO("Two"),
    THREE("Three"),
    FOUR("Four"),
    FIVE("Five");

    private final String value;

    StarRating(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

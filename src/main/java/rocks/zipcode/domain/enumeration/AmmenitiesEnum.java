package rocks.zipcode.domain.enumeration;

/**
 * The AmmenitiesEnum enumeration.
 */
public enum AmmenitiesEnum {
    BATHROOM("Bathroom"),
    FOUNTAIN("Fountain"),
    PLAYGROUND("Playground"),
    BIKETOOLS("BikeTools"),
    RESTAURANT("Restaurant");

    private final String value;

    AmmenitiesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

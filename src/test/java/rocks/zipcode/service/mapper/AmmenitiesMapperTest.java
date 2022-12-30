package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmmenitiesMapperTest {

    private AmmenitiesMapper ammenitiesMapper;

    @BeforeEach
    public void setUp() {
        ammenitiesMapper = new AmmenitiesMapperImpl();
    }
}

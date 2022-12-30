package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class AmmenitiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ammenities.class);
        Ammenities ammenities1 = new Ammenities();
        ammenities1.setId(1L);
        Ammenities ammenities2 = new Ammenities();
        ammenities2.setId(ammenities1.getId());
        assertThat(ammenities1).isEqualTo(ammenities2);
        ammenities2.setId(2L);
        assertThat(ammenities1).isNotEqualTo(ammenities2);
        ammenities1.setId(null);
        assertThat(ammenities1).isNotEqualTo(ammenities2);
    }
}

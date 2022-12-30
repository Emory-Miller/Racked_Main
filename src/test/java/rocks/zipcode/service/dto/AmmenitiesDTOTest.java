package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class AmmenitiesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmmenitiesDTO.class);
        AmmenitiesDTO ammenitiesDTO1 = new AmmenitiesDTO();
        ammenitiesDTO1.setId(1L);
        AmmenitiesDTO ammenitiesDTO2 = new AmmenitiesDTO();
        assertThat(ammenitiesDTO1).isNotEqualTo(ammenitiesDTO2);
        ammenitiesDTO2.setId(ammenitiesDTO1.getId());
        assertThat(ammenitiesDTO1).isEqualTo(ammenitiesDTO2);
        ammenitiesDTO2.setId(2L);
        assertThat(ammenitiesDTO1).isNotEqualTo(ammenitiesDTO2);
        ammenitiesDTO1.setId(null);
        assertThat(ammenitiesDTO1).isNotEqualTo(ammenitiesDTO2);
    }
}

package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class PostRackDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostRackDTO.class);
        PostRackDTO postRackDTO1 = new PostRackDTO();
        postRackDTO1.setId(1L);
        PostRackDTO postRackDTO2 = new PostRackDTO();
        assertThat(postRackDTO1).isNotEqualTo(postRackDTO2);
        postRackDTO2.setId(postRackDTO1.getId());
        assertThat(postRackDTO1).isEqualTo(postRackDTO2);
        postRackDTO2.setId(2L);
        assertThat(postRackDTO1).isNotEqualTo(postRackDTO2);
        postRackDTO1.setId(null);
        assertThat(postRackDTO1).isNotEqualTo(postRackDTO2);
    }
}

package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class PostRackTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostRack.class);
        PostRack postRack1 = new PostRack();
        postRack1.setId(1L);
        PostRack postRack2 = new PostRack();
        postRack2.setId(postRack1.getId());
        assertThat(postRack1).isEqualTo(postRack2);
        postRack2.setId(2L);
        assertThat(postRack1).isNotEqualTo(postRack2);
        postRack1.setId(null);
        assertThat(postRack1).isNotEqualTo(postRack2);
    }
}

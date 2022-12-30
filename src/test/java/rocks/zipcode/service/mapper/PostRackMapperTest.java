package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostRackMapperTest {

    private PostRackMapper postRackMapper;

    @BeforeEach
    public void setUp() {
        postRackMapper = new PostRackMapperImpl();
    }
}

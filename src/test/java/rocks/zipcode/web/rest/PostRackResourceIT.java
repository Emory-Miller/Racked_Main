package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.PostRack;
import rocks.zipcode.repository.PostRackRepository;
import rocks.zipcode.service.dto.PostRackDTO;
import rocks.zipcode.service.mapper.PostRackMapper;

/**
 * Integration tests for the {@link PostRackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PostRackResourceIT {

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/post-racks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostRackRepository postRackRepository;

    @Autowired
    private PostRackMapper postRackMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostRackMockMvc;

    private PostRack postRack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostRack createEntity(EntityManager em) {
        PostRack postRack = new PostRack()
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return postRack;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostRack createUpdatedEntity(EntityManager em) {
        PostRack postRack = new PostRack()
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return postRack;
    }

    @BeforeEach
    public void initTest() {
        postRack = createEntity(em);
    }

    @Test
    @Transactional
    void createPostRack() throws Exception {
        int databaseSizeBeforeCreate = postRackRepository.findAll().size();
        // Create the PostRack
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);
        restPostRackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postRackDTO)))
            .andExpect(status().isCreated());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeCreate + 1);
        PostRack testPostRack = postRackList.get(postRackList.size() - 1);
        assertThat(testPostRack.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testPostRack.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPostRack.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPostRack.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPostRackWithExistingId() throws Exception {
        // Create the PostRack with an existing ID
        postRack.setId(1L);
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);

        int databaseSizeBeforeCreate = postRackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostRackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postRackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPostRacks() throws Exception {
        // Initialize the database
        postRackRepository.saveAndFlush(postRack);

        // Get all the postRackList
        restPostRackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postRack.getId().intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getPostRack() throws Exception {
        // Initialize the database
        postRackRepository.saveAndFlush(postRack);

        // Get the postRack
        restPostRackMockMvc
            .perform(get(ENTITY_API_URL_ID, postRack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postRack.getId().intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingPostRack() throws Exception {
        // Get the postRack
        restPostRackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPostRack() throws Exception {
        // Initialize the database
        postRackRepository.saveAndFlush(postRack);

        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();

        // Update the postRack
        PostRack updatedPostRack = postRackRepository.findById(postRack.getId()).get();
        // Disconnect from session so that the updates on updatedPostRack are not directly saved in db
        em.detach(updatedPostRack);
        updatedPostRack
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        PostRackDTO postRackDTO = postRackMapper.toDto(updatedPostRack);

        restPostRackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postRackDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postRackDTO))
            )
            .andExpect(status().isOk());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
        PostRack testPostRack = postRackList.get(postRackList.size() - 1);
        assertThat(testPostRack.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPostRack.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPostRack.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPostRack.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPostRack() throws Exception {
        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();
        postRack.setId(count.incrementAndGet());

        // Create the PostRack
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostRackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postRackDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postRackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostRack() throws Exception {
        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();
        postRack.setId(count.incrementAndGet());

        // Create the PostRack
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostRackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postRackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostRack() throws Exception {
        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();
        postRack.setId(count.incrementAndGet());

        // Create the PostRack
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostRackMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postRackDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostRackWithPatch() throws Exception {
        // Initialize the database
        postRackRepository.saveAndFlush(postRack);

        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();

        // Update the postRack using partial update
        PostRack partialUpdatedPostRack = new PostRack();
        partialUpdatedPostRack.setId(postRack.getId());

        partialUpdatedPostRack
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPostRackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostRack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostRack))
            )
            .andExpect(status().isOk());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
        PostRack testPostRack = postRackList.get(postRackList.size() - 1);
        assertThat(testPostRack.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPostRack.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPostRack.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPostRack.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePostRackWithPatch() throws Exception {
        // Initialize the database
        postRackRepository.saveAndFlush(postRack);

        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();

        // Update the postRack using partial update
        PostRack partialUpdatedPostRack = new PostRack();
        partialUpdatedPostRack.setId(postRack.getId());

        partialUpdatedPostRack
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPostRackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostRack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostRack))
            )
            .andExpect(status().isOk());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
        PostRack testPostRack = postRackList.get(postRackList.size() - 1);
        assertThat(testPostRack.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPostRack.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPostRack.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPostRack.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPostRack() throws Exception {
        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();
        postRack.setId(count.incrementAndGet());

        // Create the PostRack
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostRackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postRackDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postRackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostRack() throws Exception {
        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();
        postRack.setId(count.incrementAndGet());

        // Create the PostRack
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostRackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postRackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostRack() throws Exception {
        int databaseSizeBeforeUpdate = postRackRepository.findAll().size();
        postRack.setId(count.incrementAndGet());

        // Create the PostRack
        PostRackDTO postRackDTO = postRackMapper.toDto(postRack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostRackMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postRackDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostRack in the database
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePostRack() throws Exception {
        // Initialize the database
        postRackRepository.saveAndFlush(postRack);

        int databaseSizeBeforeDelete = postRackRepository.findAll().size();

        // Delete the postRack
        restPostRackMockMvc
            .perform(delete(ENTITY_API_URL_ID, postRack.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostRack> postRackList = postRackRepository.findAll();
        assertThat(postRackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

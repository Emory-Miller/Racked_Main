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
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.Ammenities;
import rocks.zipcode.domain.enumeration.AmmenitiesEnum;
import rocks.zipcode.repository.AmmenitiesRepository;
import rocks.zipcode.service.dto.AmmenitiesDTO;
import rocks.zipcode.service.mapper.AmmenitiesMapper;

/**
 * Integration tests for the {@link AmmenitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AmmenitiesResourceIT {

    private static final AmmenitiesEnum DEFAULT_AMMENITY = AmmenitiesEnum.BATHROOM;
    private static final AmmenitiesEnum UPDATED_AMMENITY = AmmenitiesEnum.FOUNTAIN;

    private static final String ENTITY_API_URL = "/api/ammenities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AmmenitiesRepository ammenitiesRepository;

    @Autowired
    private AmmenitiesMapper ammenitiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmmenitiesMockMvc;

    private Ammenities ammenities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ammenities createEntity(EntityManager em) {
        Ammenities ammenities = new Ammenities().ammenity(DEFAULT_AMMENITY);
        return ammenities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ammenities createUpdatedEntity(EntityManager em) {
        Ammenities ammenities = new Ammenities().ammenity(UPDATED_AMMENITY);
        return ammenities;
    }

    @BeforeEach
    public void initTest() {
        ammenities = createEntity(em);
    }

    @Test
    @Transactional
    void createAmmenities() throws Exception {
        int databaseSizeBeforeCreate = ammenitiesRepository.findAll().size();
        // Create the Ammenities
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);
        restAmmenitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Ammenities testAmmenities = ammenitiesList.get(ammenitiesList.size() - 1);
        assertThat(testAmmenities.getAmmenity()).isEqualTo(DEFAULT_AMMENITY);
    }

    @Test
    @Transactional
    void createAmmenitiesWithExistingId() throws Exception {
        // Create the Ammenities with an existing ID
        ammenities.setId(1L);
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);

        int databaseSizeBeforeCreate = ammenitiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmmenitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAmmenities() throws Exception {
        // Initialize the database
        ammenitiesRepository.saveAndFlush(ammenities);

        // Get all the ammenitiesList
        restAmmenitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ammenities.getId().intValue())))
            .andExpect(jsonPath("$.[*].ammenity").value(hasItem(DEFAULT_AMMENITY.toString())));
    }

    @Test
    @Transactional
    void getAmmenities() throws Exception {
        // Initialize the database
        ammenitiesRepository.saveAndFlush(ammenities);

        // Get the ammenities
        restAmmenitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, ammenities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ammenities.getId().intValue()))
            .andExpect(jsonPath("$.ammenity").value(DEFAULT_AMMENITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAmmenities() throws Exception {
        // Get the ammenities
        restAmmenitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAmmenities() throws Exception {
        // Initialize the database
        ammenitiesRepository.saveAndFlush(ammenities);

        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();

        // Update the ammenities
        Ammenities updatedAmmenities = ammenitiesRepository.findById(ammenities.getId()).get();
        // Disconnect from session so that the updates on updatedAmmenities are not directly saved in db
        em.detach(updatedAmmenities);
        updatedAmmenities.ammenity(UPDATED_AMMENITY);
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(updatedAmmenities);

        restAmmenitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ammenitiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
        Ammenities testAmmenities = ammenitiesList.get(ammenitiesList.size() - 1);
        assertThat(testAmmenities.getAmmenity()).isEqualTo(UPDATED_AMMENITY);
    }

    @Test
    @Transactional
    void putNonExistingAmmenities() throws Exception {
        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();
        ammenities.setId(count.incrementAndGet());

        // Create the Ammenities
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmmenitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ammenitiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAmmenities() throws Exception {
        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();
        ammenities.setId(count.incrementAndGet());

        // Create the Ammenities
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmmenitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAmmenities() throws Exception {
        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();
        ammenities.setId(count.incrementAndGet());

        // Create the Ammenities
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmmenitiesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAmmenitiesWithPatch() throws Exception {
        // Initialize the database
        ammenitiesRepository.saveAndFlush(ammenities);

        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();

        // Update the ammenities using partial update
        Ammenities partialUpdatedAmmenities = new Ammenities();
        partialUpdatedAmmenities.setId(ammenities.getId());

        partialUpdatedAmmenities.ammenity(UPDATED_AMMENITY);

        restAmmenitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmmenities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmmenities))
            )
            .andExpect(status().isOk());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
        Ammenities testAmmenities = ammenitiesList.get(ammenitiesList.size() - 1);
        assertThat(testAmmenities.getAmmenity()).isEqualTo(UPDATED_AMMENITY);
    }

    @Test
    @Transactional
    void fullUpdateAmmenitiesWithPatch() throws Exception {
        // Initialize the database
        ammenitiesRepository.saveAndFlush(ammenities);

        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();

        // Update the ammenities using partial update
        Ammenities partialUpdatedAmmenities = new Ammenities();
        partialUpdatedAmmenities.setId(ammenities.getId());

        partialUpdatedAmmenities.ammenity(UPDATED_AMMENITY);

        restAmmenitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmmenities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmmenities))
            )
            .andExpect(status().isOk());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
        Ammenities testAmmenities = ammenitiesList.get(ammenitiesList.size() - 1);
        assertThat(testAmmenities.getAmmenity()).isEqualTo(UPDATED_AMMENITY);
    }

    @Test
    @Transactional
    void patchNonExistingAmmenities() throws Exception {
        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();
        ammenities.setId(count.incrementAndGet());

        // Create the Ammenities
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmmenitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ammenitiesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAmmenities() throws Exception {
        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();
        ammenities.setId(count.incrementAndGet());

        // Create the Ammenities
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmmenitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAmmenities() throws Exception {
        int databaseSizeBeforeUpdate = ammenitiesRepository.findAll().size();
        ammenities.setId(count.incrementAndGet());

        // Create the Ammenities
        AmmenitiesDTO ammenitiesDTO = ammenitiesMapper.toDto(ammenities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmmenitiesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ammenitiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ammenities in the database
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAmmenities() throws Exception {
        // Initialize the database
        ammenitiesRepository.saveAndFlush(ammenities);

        int databaseSizeBeforeDelete = ammenitiesRepository.findAll().size();

        // Delete the ammenities
        restAmmenitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, ammenities.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ammenities> ammenitiesList = ammenitiesRepository.findAll();
        assertThat(ammenitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

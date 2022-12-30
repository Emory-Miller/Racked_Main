package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.repository.AmmenitiesRepository;
import rocks.zipcode.service.AmmenitiesService;
import rocks.zipcode.service.dto.AmmenitiesDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Ammenities}.
 */
@RestController
@RequestMapping("/api")
public class AmmenitiesResource {

    private final Logger log = LoggerFactory.getLogger(AmmenitiesResource.class);

    private static final String ENTITY_NAME = "ammenities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmmenitiesService ammenitiesService;

    private final AmmenitiesRepository ammenitiesRepository;

    public AmmenitiesResource(AmmenitiesService ammenitiesService, AmmenitiesRepository ammenitiesRepository) {
        this.ammenitiesService = ammenitiesService;
        this.ammenitiesRepository = ammenitiesRepository;
    }

    /**
     * {@code POST  /ammenities} : Create a new ammenities.
     *
     * @param ammenitiesDTO the ammenitiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ammenitiesDTO, or with status {@code 400 (Bad Request)} if the ammenities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ammenities")
    public ResponseEntity<AmmenitiesDTO> createAmmenities(@RequestBody AmmenitiesDTO ammenitiesDTO) throws URISyntaxException {
        log.debug("REST request to save Ammenities : {}", ammenitiesDTO);
        if (ammenitiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new ammenities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmmenitiesDTO result = ammenitiesService.save(ammenitiesDTO);
        return ResponseEntity
            .created(new URI("/api/ammenities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ammenities/:id} : Updates an existing ammenities.
     *
     * @param id the id of the ammenitiesDTO to save.
     * @param ammenitiesDTO the ammenitiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ammenitiesDTO,
     * or with status {@code 400 (Bad Request)} if the ammenitiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ammenitiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ammenities/{id}")
    public ResponseEntity<AmmenitiesDTO> updateAmmenities(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmmenitiesDTO ammenitiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ammenities : {}, {}", id, ammenitiesDTO);
        if (ammenitiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ammenitiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ammenitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AmmenitiesDTO result = ammenitiesService.update(ammenitiesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ammenitiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ammenities/:id} : Partial updates given fields of an existing ammenities, field will ignore if it is null
     *
     * @param id the id of the ammenitiesDTO to save.
     * @param ammenitiesDTO the ammenitiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ammenitiesDTO,
     * or with status {@code 400 (Bad Request)} if the ammenitiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ammenitiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ammenitiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ammenities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AmmenitiesDTO> partialUpdateAmmenities(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmmenitiesDTO ammenitiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ammenities partially : {}, {}", id, ammenitiesDTO);
        if (ammenitiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ammenitiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ammenitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AmmenitiesDTO> result = ammenitiesService.partialUpdate(ammenitiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ammenitiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ammenities} : get all the ammenities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ammenities in body.
     */
    @GetMapping("/ammenities")
    public List<AmmenitiesDTO> getAllAmmenities() {
        log.debug("REST request to get all Ammenities");
        return ammenitiesService.findAll();
    }

    /**
     * {@code GET  /ammenities/:id} : get the "id" ammenities.
     *
     * @param id the id of the ammenitiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ammenitiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ammenities/{id}")
    public ResponseEntity<AmmenitiesDTO> getAmmenities(@PathVariable Long id) {
        log.debug("REST request to get Ammenities : {}", id);
        Optional<AmmenitiesDTO> ammenitiesDTO = ammenitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ammenitiesDTO);
    }

    /**
     * {@code DELETE  /ammenities/:id} : delete the "id" ammenities.
     *
     * @param id the id of the ammenitiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ammenities/{id}")
    public ResponseEntity<Void> deleteAmmenities(@PathVariable Long id) {
        log.debug("REST request to delete Ammenities : {}", id);
        ammenitiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

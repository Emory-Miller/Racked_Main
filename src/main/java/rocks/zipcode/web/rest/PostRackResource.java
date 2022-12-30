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
import rocks.zipcode.repository.PostRackRepository;
import rocks.zipcode.service.PostRackService;
import rocks.zipcode.service.dto.PostRackDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.PostRack}.
 */
@RestController
@RequestMapping("/api")
public class PostRackResource {

    private final Logger log = LoggerFactory.getLogger(PostRackResource.class);

    private static final String ENTITY_NAME = "postRack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostRackService postRackService;

    private final PostRackRepository postRackRepository;

    public PostRackResource(PostRackService postRackService, PostRackRepository postRackRepository) {
        this.postRackService = postRackService;
        this.postRackRepository = postRackRepository;
    }

    /**
     * {@code POST  /post-racks} : Create a new postRack.
     *
     * @param postRackDTO the postRackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postRackDTO, or with status {@code 400 (Bad Request)} if the postRack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-racks")
    public ResponseEntity<PostRackDTO> createPostRack(@RequestBody PostRackDTO postRackDTO) throws URISyntaxException {
        log.debug("REST request to save PostRack : {}", postRackDTO);
        if (postRackDTO.getId() != null) {
            throw new BadRequestAlertException("A new postRack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostRackDTO result = postRackService.save(postRackDTO);
        return ResponseEntity
            .created(new URI("/api/post-racks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-racks/:id} : Updates an existing postRack.
     *
     * @param id the id of the postRackDTO to save.
     * @param postRackDTO the postRackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postRackDTO,
     * or with status {@code 400 (Bad Request)} if the postRackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postRackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-racks/{id}")
    public ResponseEntity<PostRackDTO> updatePostRack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostRackDTO postRackDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PostRack : {}, {}", id, postRackDTO);
        if (postRackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postRackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postRackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostRackDTO result = postRackService.update(postRackDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postRackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /post-racks/:id} : Partial updates given fields of an existing postRack, field will ignore if it is null
     *
     * @param id the id of the postRackDTO to save.
     * @param postRackDTO the postRackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postRackDTO,
     * or with status {@code 400 (Bad Request)} if the postRackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postRackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postRackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/post-racks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostRackDTO> partialUpdatePostRack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostRackDTO postRackDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PostRack partially : {}, {}", id, postRackDTO);
        if (postRackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postRackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postRackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostRackDTO> result = postRackService.partialUpdate(postRackDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postRackDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /post-racks} : get all the postRacks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postRacks in body.
     */
    @GetMapping("/post-racks")
    public List<PostRackDTO> getAllPostRacks() {
        log.debug("REST request to get all PostRacks");
        return postRackService.findAll();
    }

    /**
     * {@code GET  /post-racks/:id} : get the "id" postRack.
     *
     * @param id the id of the postRackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postRackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-racks/{id}")
    public ResponseEntity<PostRackDTO> getPostRack(@PathVariable Long id) {
        log.debug("REST request to get PostRack : {}", id);
        Optional<PostRackDTO> postRackDTO = postRackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postRackDTO);
    }

    /**
     * {@code DELETE  /post-racks/:id} : delete the "id" postRack.
     *
     * @param id the id of the postRackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-racks/{id}")
    public ResponseEntity<Void> deletePostRack(@PathVariable Long id) {
        log.debug("REST request to delete PostRack : {}", id);
        postRackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

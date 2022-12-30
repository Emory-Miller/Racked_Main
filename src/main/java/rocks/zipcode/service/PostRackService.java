package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.PostRack;
import rocks.zipcode.repository.PostRackRepository;
import rocks.zipcode.service.dto.PostRackDTO;
import rocks.zipcode.service.mapper.PostRackMapper;

/**
 * Service Implementation for managing {@link PostRack}.
 */
@Service
@Transactional
public class PostRackService {

    private final Logger log = LoggerFactory.getLogger(PostRackService.class);

    private final PostRackRepository postRackRepository;

    private final PostRackMapper postRackMapper;

    public PostRackService(PostRackRepository postRackRepository, PostRackMapper postRackMapper) {
        this.postRackRepository = postRackRepository;
        this.postRackMapper = postRackMapper;
    }

    /**
     * Save a postRack.
     *
     * @param postRackDTO the entity to save.
     * @return the persisted entity.
     */
    public PostRackDTO save(PostRackDTO postRackDTO) {
        log.debug("Request to save PostRack : {}", postRackDTO);
        PostRack postRack = postRackMapper.toEntity(postRackDTO);
        postRack = postRackRepository.save(postRack);
        return postRackMapper.toDto(postRack);
    }

    /**
     * Update a postRack.
     *
     * @param postRackDTO the entity to save.
     * @return the persisted entity.
     */
    public PostRackDTO update(PostRackDTO postRackDTO) {
        log.debug("Request to update PostRack : {}", postRackDTO);
        PostRack postRack = postRackMapper.toEntity(postRackDTO);
        postRack = postRackRepository.save(postRack);
        return postRackMapper.toDto(postRack);
    }

    /**
     * Partially update a postRack.
     *
     * @param postRackDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PostRackDTO> partialUpdate(PostRackDTO postRackDTO) {
        log.debug("Request to partially update PostRack : {}", postRackDTO);

        return postRackRepository
            .findById(postRackDTO.getId())
            .map(existingPostRack -> {
                postRackMapper.partialUpdate(existingPostRack, postRackDTO);

                return existingPostRack;
            })
            .map(postRackRepository::save)
            .map(postRackMapper::toDto);
    }

    /**
     * Get all the postRacks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PostRackDTO> findAll() {
        log.debug("Request to get all PostRacks");
        return postRackRepository.findAll().stream().map(postRackMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one postRack by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostRackDTO> findOne(Long id) {
        log.debug("Request to get PostRack : {}", id);
        return postRackRepository.findById(id).map(postRackMapper::toDto);
    }

    /**
     * Delete the postRack by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PostRack : {}", id);
        postRackRepository.deleteById(id);
    }
}

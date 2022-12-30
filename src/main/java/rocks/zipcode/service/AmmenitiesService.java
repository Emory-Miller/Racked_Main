package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Ammenities;
import rocks.zipcode.repository.AmmenitiesRepository;
import rocks.zipcode.service.dto.AmmenitiesDTO;
import rocks.zipcode.service.mapper.AmmenitiesMapper;

/**
 * Service Implementation for managing {@link Ammenities}.
 */
@Service
@Transactional
public class AmmenitiesService {

    private final Logger log = LoggerFactory.getLogger(AmmenitiesService.class);

    private final AmmenitiesRepository ammenitiesRepository;

    private final AmmenitiesMapper ammenitiesMapper;

    public AmmenitiesService(AmmenitiesRepository ammenitiesRepository, AmmenitiesMapper ammenitiesMapper) {
        this.ammenitiesRepository = ammenitiesRepository;
        this.ammenitiesMapper = ammenitiesMapper;
    }

    /**
     * Save a ammenities.
     *
     * @param ammenitiesDTO the entity to save.
     * @return the persisted entity.
     */
    public AmmenitiesDTO save(AmmenitiesDTO ammenitiesDTO) {
        log.debug("Request to save Ammenities : {}", ammenitiesDTO);
        Ammenities ammenities = ammenitiesMapper.toEntity(ammenitiesDTO);
        ammenities = ammenitiesRepository.save(ammenities);
        return ammenitiesMapper.toDto(ammenities);
    }

    /**
     * Update a ammenities.
     *
     * @param ammenitiesDTO the entity to save.
     * @return the persisted entity.
     */
    public AmmenitiesDTO update(AmmenitiesDTO ammenitiesDTO) {
        log.debug("Request to update Ammenities : {}", ammenitiesDTO);
        Ammenities ammenities = ammenitiesMapper.toEntity(ammenitiesDTO);
        ammenities = ammenitiesRepository.save(ammenities);
        return ammenitiesMapper.toDto(ammenities);
    }

    /**
     * Partially update a ammenities.
     *
     * @param ammenitiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AmmenitiesDTO> partialUpdate(AmmenitiesDTO ammenitiesDTO) {
        log.debug("Request to partially update Ammenities : {}", ammenitiesDTO);

        return ammenitiesRepository
            .findById(ammenitiesDTO.getId())
            .map(existingAmmenities -> {
                ammenitiesMapper.partialUpdate(existingAmmenities, ammenitiesDTO);

                return existingAmmenities;
            })
            .map(ammenitiesRepository::save)
            .map(ammenitiesMapper::toDto);
    }

    /**
     * Get all the ammenities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AmmenitiesDTO> findAll() {
        log.debug("Request to get all Ammenities");
        return ammenitiesRepository.findAll().stream().map(ammenitiesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ammenities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AmmenitiesDTO> findOne(Long id) {
        log.debug("Request to get Ammenities : {}", id);
        return ammenitiesRepository.findById(id).map(ammenitiesMapper::toDto);
    }

    /**
     * Delete the ammenities by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ammenities : {}", id);
        ammenitiesRepository.deleteById(id);
    }
}

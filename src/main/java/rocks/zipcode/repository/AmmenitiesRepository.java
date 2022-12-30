package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.Ammenities;

/**
 * Spring Data JPA repository for the Ammenities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmmenitiesRepository extends JpaRepository<Ammenities, Long> {}

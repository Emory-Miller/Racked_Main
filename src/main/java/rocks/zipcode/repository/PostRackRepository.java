package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.PostRack;

/**
 * Spring Data JPA repository for the PostRack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRackRepository extends JpaRepository<PostRack, Long> {}

package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.PostRack;
import rocks.zipcode.service.dto.PostRackDTO;

/**
 * Mapper for the entity {@link PostRack} and its DTO {@link PostRackDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostRackMapper extends EntityMapper<PostRackDTO, PostRack> {}

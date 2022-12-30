package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Ammenities;
import rocks.zipcode.domain.Review;
import rocks.zipcode.service.dto.AmmenitiesDTO;
import rocks.zipcode.service.dto.ReviewDTO;

/**
 * Mapper for the entity {@link Ammenities} and its DTO {@link AmmenitiesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AmmenitiesMapper extends EntityMapper<AmmenitiesDTO, Ammenities> {
    @Mapping(target = "review", source = "review", qualifiedByName = "reviewId")
    AmmenitiesDTO toDto(Ammenities s);

    @Named("reviewId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReviewDTO toDtoReviewId(Review review);
}

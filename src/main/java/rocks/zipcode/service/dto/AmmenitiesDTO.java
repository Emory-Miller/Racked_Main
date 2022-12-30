package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import rocks.zipcode.domain.enumeration.AmmenitiesEnum;

/**
 * A DTO for the {@link rocks.zipcode.domain.Ammenities} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AmmenitiesDTO implements Serializable {

    private Long id;

    private AmmenitiesEnum ammenity;

    private ReviewDTO review;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AmmenitiesEnum getAmmenity() {
        return ammenity;
    }

    public void setAmmenity(AmmenitiesEnum ammenity) {
        this.ammenity = ammenity;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public void setReview(ReviewDTO review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmmenitiesDTO)) {
            return false;
        }

        AmmenitiesDTO ammenitiesDTO = (AmmenitiesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ammenitiesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmmenitiesDTO{" +
            "id=" + getId() +
            ", ammenity='" + getAmmenity() + "'" +
            ", review=" + getReview() +
            "}";
    }
}

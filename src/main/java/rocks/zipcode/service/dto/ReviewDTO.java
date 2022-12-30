package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import rocks.zipcode.domain.enumeration.RackSize;
import rocks.zipcode.domain.enumeration.StarRating;

/**
 * A DTO for the {@link rocks.zipcode.domain.Review} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReviewDTO implements Serializable {

    private Long id;

    private StarRating starRating;

    private RackSize rackSize;

    private String comments;

    private UserDTO user;

    private PostRackDTO postRack;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StarRating getStarRating() {
        return starRating;
    }

    public void setStarRating(StarRating starRating) {
        this.starRating = starRating;
    }

    public RackSize getRackSize() {
        return rackSize;
    }

    public void setRackSize(RackSize rackSize) {
        this.rackSize = rackSize;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PostRackDTO getPostRack() {
        return postRack;
    }

    public void setPostRack(PostRackDTO postRack) {
        this.postRack = postRack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewDTO)) {
            return false;
        }

        ReviewDTO reviewDTO = (ReviewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewDTO{" +
            "id=" + getId() +
            ", starRating='" + getStarRating() + "'" +
            ", rackSize='" + getRackSize() + "'" +
            ", comments='" + getComments() + "'" +
            ", user=" + getUser() +
            ", postRack=" + getPostRack() +
            "}";
    }
}

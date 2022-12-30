package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link rocks.zipcode.domain.PostRack} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostRackDTO implements Serializable {

    private Long id;

    private Double longitude;

    private Double latitude;

    @Lob
    private byte[] image;

    private String imageContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostRackDTO)) {
            return false;
        }

        PostRackDTO postRackDTO = (PostRackDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postRackDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostRackDTO{" +
            "id=" + getId() +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", image='" + getImage() + "'" +
            "}";
    }
}

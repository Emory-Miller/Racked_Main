package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import rocks.zipcode.domain.enumeration.AmmenitiesEnum;

/**
 * A Ammenities.
 */
@Entity
@Table(name = "ammenities")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ammenities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ammenity")
    private AmmenitiesEnum ammenity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "postRack", "ammenities" }, allowSetters = true)
    private Review review;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ammenities id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AmmenitiesEnum getAmmenity() {
        return this.ammenity;
    }

    public Ammenities ammenity(AmmenitiesEnum ammenity) {
        this.setAmmenity(ammenity);
        return this;
    }

    public void setAmmenity(AmmenitiesEnum ammenity) {
        this.ammenity = ammenity;
    }

    public Review getReview() {
        return this.review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Ammenities review(Review review) {
        this.setReview(review);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ammenities)) {
            return false;
        }
        return id != null && id.equals(((Ammenities) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ammenities{" +
            "id=" + getId() +
            ", ammenity='" + getAmmenity() + "'" +
            "}";
    }
}

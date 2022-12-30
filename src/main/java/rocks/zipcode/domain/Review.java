package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import rocks.zipcode.domain.enumeration.RackSize;
import rocks.zipcode.domain.enumeration.StarRating;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "star_rating")
    private StarRating starRating;

    @Enumerated(EnumType.STRING)
    @Column(name = "rack_size")
    private RackSize rackSize;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reviews" }, allowSetters = true)
    private PostRack postRack;

    @OneToMany(mappedBy = "review")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "review" }, allowSetters = true)
    private Set<Ammenities> ammenities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Review id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StarRating getStarRating() {
        return this.starRating;
    }

    public Review starRating(StarRating starRating) {
        this.setStarRating(starRating);
        return this;
    }

    public void setStarRating(StarRating starRating) {
        this.starRating = starRating;
    }

    public RackSize getRackSize() {
        return this.rackSize;
    }

    public Review rackSize(RackSize rackSize) {
        this.setRackSize(rackSize);
        return this;
    }

    public void setRackSize(RackSize rackSize) {
        this.rackSize = rackSize;
    }

    public String getComments() {
        return this.comments;
    }

    public Review comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review user(User user) {
        this.setUser(user);
        return this;
    }

    public PostRack getPostRack() {
        return this.postRack;
    }

    public void setPostRack(PostRack postRack) {
        this.postRack = postRack;
    }

    public Review postRack(PostRack postRack) {
        this.setPostRack(postRack);
        return this;
    }

    public Set<Ammenities> getAmmenities() {
        return this.ammenities;
    }

    public void setAmmenities(Set<Ammenities> ammenities) {
        if (this.ammenities != null) {
            this.ammenities.forEach(i -> i.setReview(null));
        }
        if (ammenities != null) {
            ammenities.forEach(i -> i.setReview(this));
        }
        this.ammenities = ammenities;
    }

    public Review ammenities(Set<Ammenities> ammenities) {
        this.setAmmenities(ammenities);
        return this;
    }

    public Review addAmmenities(Ammenities ammenities) {
        this.ammenities.add(ammenities);
        ammenities.setReview(this);
        return this;
    }

    public Review removeAmmenities(Ammenities ammenities) {
        this.ammenities.remove(ammenities);
        ammenities.setReview(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }
        return id != null && id.equals(((Review) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", starRating='" + getStarRating() + "'" +
            ", rackSize='" + getRackSize() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}

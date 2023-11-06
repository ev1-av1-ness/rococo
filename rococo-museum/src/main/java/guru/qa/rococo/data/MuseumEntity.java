package guru.qa.rococo.data;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "museum")
public class MuseumEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "museum_id", columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), TRUE))")
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geo_id", nullable = false)
    private GeoEntity geo;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "museum")
//    private Set<PaintingEntity> paintings;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public GeoEntity getGeo() {
        return geo;
    }

    public void setGeo(GeoEntity geo) {
        this.geo = geo;
    }

//    public Set<PaintingEntity> getPaintings() {
//        return paintings;
//    }
//
//    public void setPaintings(Set<PaintingEntity> paintings) {
//        this.paintings = paintings;
//    }
}

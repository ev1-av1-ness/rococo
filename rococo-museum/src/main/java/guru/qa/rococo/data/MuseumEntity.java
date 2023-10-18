package guru.qa.rococo.data;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "museum")
public class MuseumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geo_id")
    private GeoEntity geo;

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
}

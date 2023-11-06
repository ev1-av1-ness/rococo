package guru.qa.rococo.data;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "artist", schema = "rococo-artist")
public class ArtistEntity {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "artist_id", columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), TRUE))")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "biography", nullable = false)
    private String biography;

    @Column(name = "photo", nullable = false)
    private byte[] photo;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist")
//    private Set<PaintingEntity> paintings;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

//    public Set<PaintingEntity> getPaintings() {
//        return paintings;
//    }

//    public void setPaintings(Set<PaintingEntity> paintings) {
//        this.paintings = paintings;
//    }
}

package guru.qa.rococo.data;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "artist", schema = "rococo-artist")
public class ArtistEntity {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), TRUE))")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "biography", nullable = false)
    private String biography;

    @Column(name = "photo", nullable = false)
    private byte[] photo;


    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}

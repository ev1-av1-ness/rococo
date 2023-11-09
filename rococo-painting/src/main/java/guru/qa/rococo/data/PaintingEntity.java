package guru.qa.rococo.data;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "painting", schema = "rococo-painting")
public class PaintingEntity {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), TRUE))")
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "museum_id", nullable = false)
    private UUID museumId;

    @Column(name = "artist_id", nullable = false)
    private UUID artistId;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setMuseumId(UUID museumId) {
        this.museumId = museumId;
    }

    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
    }
}

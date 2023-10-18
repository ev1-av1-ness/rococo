package guru.qa.rococo.data;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "painting")
public class PaintingEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content", columnDefinition = "bytea")
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "museum_id")
    private MuseumEntity museum;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

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

    public MuseumEntity getMuseum() {
        return museum;
    }

    public void setMuseum(MuseumEntity museum) {
        this.museum = museum;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    // getters and setters
}

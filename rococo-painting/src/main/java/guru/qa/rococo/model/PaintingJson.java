package guru.qa.rococo.model;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.PaintingEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "content",
        "museum",
        "artist"
})
public class PaintingJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("content")
    private String content;
    @JsonProperty("museum")
    private MuseumJson museum;
    @JsonProperty("artist")
    private ArtistJson artist;

    public PaintingJson() {
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("museum")
    public MuseumJson getMuseum() {
        return museum;
    }

    @JsonProperty("museum")
    public void setMuseum(MuseumJson museum) {
        this.museum = museum;
    }

    @JsonProperty("artist")
    public ArtistJson getArtist() {
        return artist;
    }

    @JsonProperty("artist")
    public void setArtist(ArtistJson artist) {
        this.artist = artist;
    }

    public static PaintingJson fromEntity(PaintingEntity entity) {
        PaintingJson painting = new PaintingJson();
        byte[] photo = entity.getContent();
        painting.setId(entity.getId());
        painting.setTitle(entity.getTitle());
        painting.setDescription(entity.getDescription());
        painting.setContent(photo != null && photo.length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null);
//        painting.setMuseum(entity.getMuseum()); //TODO: fix
//        painting.setArtist(entity.getArtist());
        return painting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaintingJson that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(content, that.content) && Objects.equals(museum, that.museum) && Objects.equals(artist, that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, content, museum, artist);
    }
}

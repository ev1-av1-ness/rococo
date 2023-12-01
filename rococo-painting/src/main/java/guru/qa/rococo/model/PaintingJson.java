package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.PaintingEntity;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class PaintingJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("content")
    private String content;
    @JsonProperty("museumId")
    private UUID museumId;
    @JsonProperty("artistId")
    private UUID artistId;

    public PaintingJson() {
    }

    public static PaintingJson fromEntity(PaintingEntity entity) {
        PaintingJson painting = new PaintingJson();
        byte[] photo = entity.getContent();
        painting.setId(entity.getId());
        painting.setTitle(entity.getTitle());
        painting.setDescription(entity.getDescription());
        painting.setContent(photo != null && photo.length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null);
        painting.setMuseumId(entity.getMuseumId());
        painting.setArtistId(entity.getArtistId());
        return painting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaintingJson that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(content, that.content) && Objects.equals(museumId, that.museumId) && Objects.equals(artistId, that.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, content, museumId, artistId);
    }
}

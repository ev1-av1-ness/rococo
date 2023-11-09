package guru.qa.rococo.model;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.MuseumEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MuseumJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("geoId")
    private UUID geoId;

    public MuseumJson() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MuseumJson that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(photo, that.photo) && Objects.equals(geoId, that.geoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, photo, geoId);
    }

    public static MuseumJson fromEntity(MuseumEntity entity) {
        MuseumJson museum = new MuseumJson();
        byte[] photo = entity.getPhoto();
        museum.setId(entity.getId());
        museum.setTitle(entity.getTitle());
        museum.setDescription(entity.getDescription());
        museum.setGeoId(entity.getGeoId());
        museum.setPhoto(photo != null && photo.length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null);
        return museum;
    }
}

package guru.qa.rococo.model;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import guru.qa.rococo.data.MuseumEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "photo",
        "geo"
})
public class MuseumJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("geo")
    private GeoJson geo;


    public MuseumJson() {
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

    @JsonProperty("photo")
    public String getPhoto() {
        return photo;
    }

    @JsonProperty("photo")
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @JsonProperty("geo")
    public GeoJson getGeo() {
        return geo;
    }

    @JsonProperty("geo")
    public void setGeo(GeoJson geo) {
        this.geo = geo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MuseumJson that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(photo, that.photo) && Objects.equals(geo, that.geo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, photo, geo);
    }

    public static MuseumJson fromEntity(MuseumEntity entity) {
        MuseumJson museum = new MuseumJson();
        byte[] photo = entity.getPhoto();
        museum.setId(entity.getId());
        museum.setTitle(entity.getTitle());
        museum.setDescription(entity.getDescription());
//        museum.setGeo(entity.getGeo()); //TODO: fix
        museum.setPhoto(photo != null && photo.length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null);
        return museum;
    }
}

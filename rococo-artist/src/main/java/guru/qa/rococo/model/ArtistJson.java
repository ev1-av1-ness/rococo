package guru.qa.rococo.model;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import guru.qa.rococo.data.ArtistEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "biography",
        "photo"
})
public class ArtistJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("biography")
    private String biography;
    @JsonProperty("photo")
    private String photo;


    public ArtistJson() {
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("biography")
    public String getBiography() {
        return biography;
    }

    @JsonProperty("biography")
    public void setBiography(String biography) {
        this.biography = biography;
    }

    @JsonProperty("photo")
    public String getPhoto() {
        return photo;
    }

    @JsonProperty("photo")
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static ArtistJson fromEntity(ArtistEntity entity) {
        ArtistJson artist = new ArtistJson();
        byte[] photo = entity.getPhoto();
        artist.setId(entity.getId());
        artist.setName(entity.getName());
        artist.setBiography(entity.getBiography());
        artist.setPhoto(photo != null && photo.length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null);
        return artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtistJson that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(biography, that.biography) && Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, biography, photo);
    }
}

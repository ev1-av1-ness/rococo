package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

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

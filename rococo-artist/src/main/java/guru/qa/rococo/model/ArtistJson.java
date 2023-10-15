package guru.qa.rococo.model;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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

    /**
     * No args constructor for use in serialization
     *
     */
    public ArtistJson() {
    }

    /**
     *
     * @param name
     * @param photo
     * @param id
     * @param biography
     */
    public ArtistJson(UUID id, String name, String biography, String photo) { //TODO: может быть не нужен, удалить
        super();
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.photo = photo;
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
    public int hashCode() { //TODO: поправить функцию
        int result = 1;
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.photo == null)? 0 :this.photo.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.biography == null)? 0 :this.biography.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ArtistJson rhs)) {
            return false;
        }
        return (Objects.equals(this.name, rhs.name) && Objects.equals(this.photo, rhs.photo) && Objects.equals(this.id, rhs.id) && Objects.equals(this.biography, rhs.biography));
    }
}

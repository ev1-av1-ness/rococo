package guru.qa.rococo.model;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
    private String id;
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
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public PaintingJson() {
    }

    /**
     *
     * @param museum
     * @param artist
     * @param description
     * @param id
     * @param title
     * @param content
     */
    public PaintingJson(String id,
                        String title,
                        String description,
                        String content,
                        MuseumJson museum,
                        ArtistJson artist) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.museum = museum;
        this.artist = artist;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.museum == null)? 0 :this.museum.hashCode()));
        result = ((result* 31)+((this.artist == null)? 0 :this.artist.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.content == null)? 0 :this.content.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PaintingJson rhs)) {
            return false;
        }
        return ((((((((this.museum == rhs.museum)||((this.museum!= null)&&this.museum.equals(rhs.museum)))&&((this.artist == rhs.artist)||((this.artist!= null)&&this.artist.equals(rhs.artist))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.content == rhs.content)||((this.content!= null)&&this.content.equals(rhs.content))));
    }
}

package guru.qa.rococo.model;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import guru.qa.rococo.data.CountryEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})
public class CountryJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public CountryJson() {
    }

    /**
     *
     * @param name
     * @param id
     */
    public CountryJson(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
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

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CountryJson rhs)) {
            return false;
        }
        return Objects.equals(this.name, rhs.name) && Objects.equals(this.id, rhs.id);
    }

    public static CountryJson fromEntity(CountryEntity content) {
        CountryJson country = new CountryJson();
        country.setId(content.getId());
        country.setName(content.getName());
        return country;
    }
}

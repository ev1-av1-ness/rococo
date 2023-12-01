package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.CountryEntity;

import java.util.Objects;
import java.util.UUID;

public class CountryJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;

    public CountryJson() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryJson that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static CountryJson fromEntity(CountryEntity content) {
        CountryJson country = new CountryJson();
        country.setId(content.getId());
        country.setName(content.getName());
        return country;
    }
}

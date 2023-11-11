package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.data.GeoEntity;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class GeoJson {
    private UUID id;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private CountryJson countryJson;

    public GeoJson() {
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public CountryJson getCountry() {
        return countryJson;
    }

    @JsonProperty("country")
    public void setCountry(CountryJson country) {
        this.countryJson = country;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static GeoJson fromEntity(GeoEntity entity) {
        GeoJson geo = new GeoJson();
        geo.setId(entity.getId());
        geo.setCity(entity.getCity());
        geo.setCountry(CountryJson.fromEntity(entity.getCountry()));
        return geo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoJson geoJson)) return false;
        return Objects.equals(city, geoJson.city) && Objects.equals(countryJson, geoJson.countryJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, countryJson);
    }
}

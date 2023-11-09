package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class GeoJson {

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

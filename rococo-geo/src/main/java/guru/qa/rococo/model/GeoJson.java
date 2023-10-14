package guru.qa.rococo.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "city",
        "country"
})
public class GeoJson {

    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private CountryJson countryJson;

    /**
     * No args constructor for use in serialization
     *
     */
    public GeoJson() {
    }

    /**
     *
     * @param country
     * @param city
     */
    public GeoJson(String city, CountryJson country) {
        super();
        this.city = city;
        this.countryJson = country;
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
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.countryJson == null)? 0 :this.countryJson.hashCode()));
        result = ((result* 31)+((this.city == null)? 0 :this.city.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GeoJson rhs)) {
            return false;
        }
        return (Objects.equals(this.countryJson, rhs.countryJson) &&(Objects.equals(this.city, rhs.city)));
    }
}

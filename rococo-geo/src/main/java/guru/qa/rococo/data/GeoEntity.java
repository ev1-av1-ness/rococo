package guru.qa.rococo.data;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "geo")
public class GeoEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "city")
    private String city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }
}

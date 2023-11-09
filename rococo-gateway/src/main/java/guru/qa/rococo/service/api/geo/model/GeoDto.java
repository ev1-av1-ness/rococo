package guru.qa.rococo.service.api.geo.model;

import guru.qa.rococo.model.CountryJson;
import lombok.Data;

import java.util.UUID;


@Data
public class GeoDto {
    private UUID id;
    private String city;
    private CountryJson country;
}

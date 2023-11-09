package guru.qa.rococo.service.api.geo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CountryDto {
    private UUID id;
    private String name;
}

package guru.qa.rococo.service.api.museum.model;

import lombok.Data;

import java.util.UUID;

@Data
public class MuseumDto {
    private UUID id;
    private String title;
    private String description;
    private String photo;
    private String geoId;
}

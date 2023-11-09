package guru.qa.rococo.service.api.artist.model;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ArtistDto {
    private UUID id;
    private String name;
    private String biography;
    private String photo;
}

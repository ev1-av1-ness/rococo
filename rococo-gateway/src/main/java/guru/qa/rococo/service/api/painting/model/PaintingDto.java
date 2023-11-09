package guru.qa.rococo.service.api.painting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.service.api.artist.model.ArtistDto;
import guru.qa.rococo.service.api.museum.model.MuseumDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaintingDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("content")
    private String content;
    private MuseumDto museumId;
    private ArtistDto artistId;
}

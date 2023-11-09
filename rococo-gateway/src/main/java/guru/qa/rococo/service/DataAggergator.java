package guru.qa.rococo.service;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.api.artist.ArtistClient;
import guru.qa.rococo.service.api.artist.model.ArtistDto;
import guru.qa.rococo.service.api.geo.GeoClient;
import guru.qa.rococo.service.api.geo.model.GeoDto;
import guru.qa.rococo.service.api.museum.MuseumClient;
import guru.qa.rococo.service.api.museum.model.MuseumDto;
import guru.qa.rococo.service.api.painting.PaintingClient;
import guru.qa.rococo.service.api.painting.model.PaintingDto;
import org.springframework.stereotype.Service;

@Service
public class DataAggergator {

    private final PaintingClient paintingClient;
    private final ArtistClient artistClient;
    private final MuseumClient museumClient;
    private final GeoClient geoClient;

    public DataAggergator(PaintingClient paintingClient, ArtistClient artistClient, MuseumClient museumClient, GeoClient geoClient) {
        this.paintingClient = paintingClient;
        this.artistClient = artistClient;
        this.museumClient = museumClient;
        this.geoClient = geoClient;
    }

    public PaintingJson getPainting(String id) {
        PaintingDto paintingDto = paintingClient.findPaintingById(id);
        ArtistJson artistJson = getArtist(String.valueOf(paintingDto.getArtistId()));
        MuseumJson museumJson = getMuseum(String.valueOf(paintingDto.getMuseumId()));

        PaintingJson paintingJson = new PaintingJson();
        paintingJson.setMuseum(museumJson);
        paintingJson.setArtist(artistJson);
        paintingJson.setContent(paintingDto.getContent());
        paintingJson.setDescription(paintingDto.getDescription());
        paintingJson.setTitle(paintingJson.getTitle());
        paintingJson.setId(paintingJson.getId());

        return paintingJson;
    }

    public ArtistJson getArtist(String id) {
        ArtistDto artistDto = artistClient.findArtistById(id);

        ArtistJson artistJson = new ArtistJson();
        artistJson.setId(artistDto.getId());
        artistJson.setPhoto(artistJson.getPhoto());
        artistJson.setBiography(artistJson.getBiography());
        artistJson.setName(artistJson.getName());
        return artistJson;
    }

    public MuseumJson getMuseum(String id) {
        MuseumDto museumDto = museumClient.findMuseumById(id);
        GeoDto geoDto = geoClient.findGeoById(museumDto.getGeoId());

        MuseumJson museumJson = new MuseumJson();
        GeoJson geoJson = new GeoJson();
        geoJson.setCity(geoDto.getCity());
        geoJson.setCountry(geoDto.getCountry());

        museumJson.setId(museumDto.getId());
        museumJson.setTitle(museumDto.getTitle());
        museumJson.setDescription(museumDto.getDescription());
        museumJson.setPhoto(museumDto.getPhoto());
        museumJson.setGeo(geoJson);

        return museumJson;
    }

    public GeoJson getGeo(String id) {
        GeoDto geoDto = geoClient.findGeoById(id);

        GeoJson geoJson = new GeoJson();
        geoJson.setCity(geoDto.getCity());
        geoJson.setCountry(geoDto.getCountry());

        return geoJson;
    }
}

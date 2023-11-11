package guru.qa.rococo.service;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.api.artist.ArtistClient;
import guru.qa.rococo.service.api.geo.GeoClient;
import guru.qa.rococo.service.api.museum.MuseumClient;
import guru.qa.rococo.service.api.painting.PaintingClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        PaintingJson paintingJson = paintingClient.findPaintingById(id);
        paintingJson.setMuseum(museumClient.findMuseumById(String.valueOf(paintingJson.getMuseumId())));
        paintingJson.setArtist(artistClient.findArtistById(String.valueOf(paintingJson.getArtistId())));

        ArtistJson artistJson = getArtist(String.valueOf(paintingJson.getArtist()));
        MuseumJson museumJson = getMuseum(String.valueOf(paintingJson.getMuseum()));

        paintingJson.setMuseum(museumJson);
        paintingJson.setArtist(artistJson);
        paintingJson.setArtistId(null);
        paintingJson.setMuseumId(null);
        paintingJson.setContent(paintingJson.getContent());
        paintingJson.setDescription(paintingJson.getDescription());
        paintingJson.setTitle(paintingJson.getTitle());
        paintingJson.setId(paintingJson.getId());

        return paintingJson;
    }

    public ArtistJson getArtist(String id) {
        ArtistJson artistJson = artistClient.findArtistById(id);

        artistJson.setId(artistJson.getId());
        artistJson.setPhoto(artistJson.getPhoto());
        artistJson.setBiography(artistJson.getBiography());
        artistJson.setName(artistJson.getName());
        return artistJson;
    }

    public MuseumJson getMuseum(String id) {
        MuseumJson museumJson = museumClient.findMuseumById(id);
        GeoJson geoJson = geoClient.findGeoById(String.valueOf(museumJson.getGeoId()));

        geoJson.setCity(geoJson.getCity());
        geoJson.setCountry(geoJson.getCountry());

        museumJson.setId(museumJson.getId());
        museumJson.setTitle(museumJson.getTitle());
        museumJson.setDescription(museumJson.getDescription());
        museumJson.setPhoto(museumJson.getPhoto());
        museumJson.setGeo(geoJson);

        return museumJson;
    }

    public GeoJson getGeo(String id) {
        GeoJson geoJson = geoClient.findGeoById(id);

        geoJson.setCity(geoJson.getCity());
        geoJson.setCountry(geoJson.getCountry());

        return geoJson;
    }

    public Page<PaintingJson> enrichPaintings(Page<PaintingJson> paintingJsonPage) {
        List<PaintingJson> enrichedPaintings = new ArrayList<>();

        for (PaintingJson paintingJson : paintingJsonPage) {
            PaintingJson enrichedPainting = getPainting(String.valueOf(paintingJson.getId()));
            enrichedPaintings.add(enrichedPainting);
        }

        return new PageImpl<>(enrichedPaintings, paintingJsonPage.getPageable(), paintingJsonPage.getTotalElements());
    }

    public Page<MuseumJson> enrichMuseums(Page<MuseumJson> museumJsonPage) {
        List<MuseumJson> enrichedMuseums = new ArrayList<>();

        for (MuseumJson museumJson : museumJsonPage) {
            MuseumJson enrichedMuseum = getMuseum(String.valueOf(museumJson.getId()));
            enrichedMuseums.add(enrichedMuseum);
        }

        return new PageImpl<>(enrichedMuseums, museumJsonPage.getPageable(), museumJsonPage.getTotalElements());
    }
}
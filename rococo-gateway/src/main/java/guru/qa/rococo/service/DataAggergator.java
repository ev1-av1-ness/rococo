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
import java.util.UUID;
import java.util.stream.Collectors;

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

    public PaintingJson enrichPainting(ArtistJson artistJson,
                                       MuseumJson museumJson,
                                       PaintingJson paintingJson) {
        paintingJson.setArtist(artistJson);
        museumJson.setGeoId(null);
        paintingJson.setMuseum(museumJson);
        paintingJson.setArtistId(null);
        paintingJson.setMuseumId(null);
        paintingJson.setContent(paintingJson.getContent());
        paintingJson.setDescription(paintingJson.getDescription());
        paintingJson.setTitle(paintingJson.getTitle());
        paintingJson.setId(paintingJson.getId());

        return paintingJson;
    }

    public PaintingJson getPainting(String id) {
        PaintingJson paintingJson = paintingClient.findPaintingById(id);
        MuseumJson museumJson = museumClient.findMuseumById(String.valueOf(paintingJson.getMuseumId()));
        ArtistJson artistJson = artistClient.findArtistById(String.valueOf(paintingJson.getArtistId()));

        return enrichPainting(artistJson, museumJson, paintingJson);
    }

    public List<PaintingJson> getPaintings(List<String> ids) {
        List<PaintingJson> paintingJsons = paintingClient.getAllByIds(ids);

        List<String> idsMuseum = paintingJsons.stream()
                .map(PaintingJson::getMuseumId)
                .map(UUID::toString)
                .toList();

        List<MuseumJson> museumJsons = museumClient.getAllByIds(idsMuseum);

        List<String> idsArtist = paintingJsons.stream()
                .map(PaintingJson::getArtistId)
                .map(UUID::toString)
                .toList();

        List<ArtistJson> artistJsons = artistClient.getAllByIds(idsArtist);

        List<PaintingJson> enrichedPaintings = new ArrayList<>();

        for (PaintingJson paintingJson : paintingJsons) {
            PaintingJson enrichedPainting = enrichPainting(
                    artistJsons.stream().filter(x -> x.getId().equals(paintingJson.getArtistId())).findFirst().get(),
                    museumJsons.stream().filter(x -> x.getId().equals(paintingJson.getMuseumId())).findFirst().get(),
                    paintingJson);
            enrichedPaintings.add(enrichedPainting);
        }

        return enrichedPaintings;
    }

    public MuseumJson enrichMuseum(GeoJson geoJson,
                                       MuseumJson museumJson) {
        geoJson.setId(null);
        geoJson.setCity(geoJson.getCity());
        geoJson.setCountry(geoJson.getCountry());

        museumJson.setId(museumJson.getId());
        museumJson.setTitle(museumJson.getTitle());
        museumJson.setDescription(museumJson.getDescription());
        museumJson.setPhoto(museumJson.getPhoto());
        museumJson.setGeo(geoJson);
        museumJson.setGeoId(null);

        return museumJson;
    }

    public MuseumJson getMuseum(String id) {
        MuseumJson museumJson = museumClient.findMuseumById(id);
        GeoJson geoJson = geoClient.findGeoById(String.valueOf(museumJson.getGeoId()));

        return enrichMuseum(geoJson, museumJson);
    }

    public List<MuseumJson> getMuseums(List<String> ids) {
        List<MuseumJson> museumJsons = museumClient.getAllByIds(ids);

        List<String> idsGeo = museumJsons.stream()
                .map(MuseumJson::getGeoId)
                .map(UUID::toString)
                .toList();

        List<GeoJson> geoJsons = geoClient.getAllByIds(idsGeo);

        List<MuseumJson> enrichedMuseums = new ArrayList<>();

        for (MuseumJson museumJson : museumJsons) {
            MuseumJson enrichedMuseum = enrichMuseum(
                    geoJsons.stream().filter(x -> x.getId().equals(museumJson.getGeoId())).findFirst().get(),
                    museumJson);
            enrichedMuseums.add(enrichedMuseum);
        }

        return enrichedMuseums;
    }

    public Page<PaintingJson> enrichPaintings(Page<PaintingJson> paintingJsonPage) {
        List<String> paintingIds = new ArrayList<>();

        for (PaintingJson paintingJson : paintingJsonPage) {
            paintingIds.add(String.valueOf(paintingJson.getId()));
        }

        List<PaintingJson> enrichedPaintings = new ArrayList<>(getPaintings(paintingIds));
        return new PageImpl<>(enrichedPaintings, paintingJsonPage.getPageable(), paintingJsonPage.getTotalElements());
    }

    public Page<MuseumJson> enrichMuseums(Page<MuseumJson> museumJsonPage) {
        List<String> museumIds = new ArrayList<>();

        for (MuseumJson museumJson : museumJsonPage) {
            museumIds.add(String.valueOf(museumJson.getId()));
        }

        List<MuseumJson> enrichedMuseums = new ArrayList<>(getMuseums(museumIds));
        return new PageImpl<>(enrichedMuseums, museumJsonPage.getPageable(), museumJsonPage.getTotalElements());
    }
}

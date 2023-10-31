package guru.qa.rococo.service;

import guru.qa.rococo.data.*;
import guru.qa.rococo.data.repository.PaintingRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.PaintingJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaintingService {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumService.class);  //TODO: доп обработка

    private final PaintingRepository paintingRepository;

    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    public @NonNull
    Page<PaintingJson> getPaintings(Pageable pageable) {
        Page<PaintingEntity> artistEntities = paintingRepository.findAll(pageable);
        return artistEntities.map(PaintingJson::fromEntity);
    }

    public @NonNull
    PaintingJson getPaintingById(String id) {
        return paintingRepository.findById(UUID.fromString(id))
                .map(PaintingJson::fromEntity)
                .orElseThrow(() -> new NotFoundException("Painting not found with id: " + id));
    }

    public @NonNull
    Page<PaintingJson> getPaintingsByTitle(String title, Pageable pageable) {
        Page<PaintingEntity> entities = paintingRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        if (entities.isEmpty()) {
            throw new NotFoundException("Painting not found with title: " + title);
        }
        return entities.map(PaintingJson::fromEntity);
    }

    public @NonNull
    PaintingJson createPainting(@NonNull PaintingJson paintingJson) {
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(paintingJson.getTitle());
        paintingEntity.setDescription(paintingJson.getDescription());
        paintingEntity.setContent(paintingJson.getContent() != null ? paintingJson.getContent().getBytes(StandardCharsets.UTF_8) : null);

        MuseumEntity museum = getMuseumEntity(paintingJson);

        ArtistEntity artist = new ArtistEntity();
        artist.setName(paintingJson.getArtist().getName());
        artist.setBiography(paintingJson.getArtist().getBiography());
        artist.setPhoto(paintingJson.getArtist().getPhoto() != null ? paintingJson.getArtist().getPhoto().getBytes(StandardCharsets.UTF_8) : null);

        paintingEntity.setMuseum(museum);
        paintingEntity.setArtist(artist);

        PaintingEntity saved = paintingRepository.save(paintingEntity);
        return PaintingJson.fromEntity(saved);
    }

    private static MuseumEntity getMuseumEntity(PaintingJson paintingJson) {
        CountryEntity countryMuseumEntity = new CountryEntity();
        countryMuseumEntity.setName(paintingJson.getMuseum().getGeo().getCountry().getName());

        GeoEntity geoMuseumEntity = new GeoEntity();
        geoMuseumEntity.setCity(paintingJson.getMuseum().getGeo().getCity());
        geoMuseumEntity.setCountry(countryMuseumEntity);

        MuseumEntity museum = new MuseumEntity();
        museum.setTitle(paintingJson.getTitle());
        museum.setDescription(paintingJson.getDescription());
        museum.setPhoto(paintingJson.getMuseum().getPhoto() != null ? paintingJson.getMuseum().getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        museum.setGeo(geoMuseumEntity);
        return museum;
    }

    public @NonNull
    PaintingJson changePainting(@NonNull PaintingJson painting) {
        Optional<PaintingEntity> paintingById = paintingRepository.findById(painting.getId());
        if (paintingById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find painting by given id: " + painting.getId());
        } else {
            PaintingEntity paintingEntity = paintingById.get();
            paintingEntity.setTitle(painting.getTitle());
            paintingEntity.setContent(painting.getContent() != null ? painting.getContent().getBytes(StandardCharsets.UTF_8) : null);
            paintingEntity.setDescription(painting.getDescription());

            ArtistEntity artist = new ArtistEntity();
            artist.setName(painting.getArtist().getName());
            artist.setBiography(painting.getArtist().getBiography());
            artist.setPhoto(painting.getArtist().getPhoto() != null ? painting.getArtist().getPhoto().getBytes(StandardCharsets.UTF_8) : null);

            paintingEntity.setMuseum(getMuseumEntity(painting));
            paintingEntity.setArtist(artist);

            return PaintingJson.fromEntity(paintingRepository.save(paintingEntity));
        }
    }
}

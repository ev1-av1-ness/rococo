package guru.qa.rococo.service;

import guru.qa.rococo.data.*;
import guru.qa.rococo.data.repository.PaintingRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.PaintingJson;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaintingService {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumService.class);  //TODO: доп обработка

    private final PaintingRepository paintingRepository;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull Page<PaintingJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
        Page<PaintingEntity> paintingEntities = (name == null)
                ? paintingRepository.findAll(pageable)
                : paintingRepository.findAllByTitleContainsIgnoreCase(name, pageable);
        return paintingEntities.map(PaintingJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public @Nonnull PaintingJson findPaintingById(@Nonnull String id) {
        return PaintingJson.fromEntity(
                paintingRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new NotFoundException("Artist not found with id: " + id)
                        )
        );
    }

    @Transactional(readOnly = true)
    public @Nonnull Page<PaintingJson> findPaintingByAuthorId(@Nonnull String artistId, @Nonnull Pageable pageable) {
        Page<PaintingEntity> paintingEntities = paintingRepository.findByArtistId(UUID.fromString(artistId), pageable);
        return paintingEntities.map(PaintingJson::fromEntity);
    }

    public @Nonnull PaintingJson createPainting(@Nonnull PaintingJson paintingJson) {
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

    private static MuseumEntity getMuseumEntity(@Nonnull PaintingJson painting) {
        CountryEntity countryMuseumEntity = new CountryEntity();
        countryMuseumEntity.setName(painting.getMuseum().getGeo().getCountry().getName());

        GeoEntity geoMuseumEntity = new GeoEntity();
        geoMuseumEntity.setCity(painting.getMuseum().getGeo().getCity());
        geoMuseumEntity.setCountry(countryMuseumEntity);

        MuseumEntity museum = new MuseumEntity();
        museum.setTitle(painting.getTitle());
        museum.setDescription(painting.getDescription());
        museum.setPhoto(painting.getMuseum().getPhoto() != null ? painting.getMuseum().getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        museum.setGeo(geoMuseumEntity);
        return museum;
    }

    public @Nonnull PaintingJson changePainting(@Nonnull PaintingJson painting) {
        Optional<PaintingEntity> paintingById = paintingRepository.findById(painting.getId());
        if (paintingById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Painting not found with id: " + painting.getId());
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

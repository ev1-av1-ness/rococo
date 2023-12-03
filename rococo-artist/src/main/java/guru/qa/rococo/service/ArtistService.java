package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.ArtistJson;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ArtistService {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull Page<ArtistJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
        Page<ArtistEntity> artistEntities = (name == null)
                ? artistRepository.findAll(pageable)
                : artistRepository.findAllByNameContainsIgnoreCase(name, pageable);
        return artistEntities.map(ArtistJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public @Nonnull List<ArtistJson> getAllByIds(@Nonnull List<String> ids) {
        List<UUID> uuidsIds = ids.stream()
                .map(UUID::fromString)
                .toList();
        return artistRepository.findAllByIds(uuidsIds)
                .stream()
                .map(ArtistJson::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public @Nonnull ArtistJson findArtistById(@Nonnull String id) {
        return ArtistJson.fromEntity(
                artistRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new NotFoundException("Artist not found with id: " + id)
            )
        );
    }

    @Transactional
    public @Nonnull ArtistJson addArtist(@Nonnull ArtistJson artist) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName(artist.getName());
        artistEntity.setBiography(artist.getBiography());
        artistEntity.setPhoto(artist.getPhoto() != null ? artist.getPhoto().getBytes(StandardCharsets.UTF_8) : null);

        ArtistEntity saved = artistRepository.save(artistEntity);
        return ArtistJson.fromEntity(saved);
    }

    @Transactional
    public @Nonnull ArtistJson updateArtist(@Nonnull ArtistJson artist) {
        Optional<ArtistEntity> artistById = artistRepository.findById(artist.getId());
        if (artistById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist not found with id: " + artist.getId());
        } else {
            ArtistEntity artistEntity = artistById.get();
            artistEntity.setName(artist.getName());
            artistEntity.setBiography(artist.getBiography());
            artistEntity.setPhoto(artist.getPhoto() != null ? artist.getPhoto().getBytes(StandardCharsets.UTF_8) : null);

            return ArtistJson.fromEntity(artistRepository.save(artistEntity));
        }
    }
}

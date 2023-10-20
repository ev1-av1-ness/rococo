package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.ArtistJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class ArtistService {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class); //TODO: доп обработка

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Page<ArtistJson> getArtists(Pageable pageable) {
        Page<ArtistEntity> artistEntities = artistRepository.findAll(pageable);
        return artistEntities.map(ArtistJson::fromEntity);
    }

    public ArtistEntity getArtistById(String id) {
        return artistRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Artist not found with id: " + id));
    }

    public ArtistEntity createArtist(@NonNull ArtistJson artistJson) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName(artistJson.getName());
        artistEntity.setBiography(artistJson.getBiography());
        artistEntity.setPhoto(artistJson.getPhoto() != null ? artistJson.getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        return artistRepository.save(artistEntity);
    }
}

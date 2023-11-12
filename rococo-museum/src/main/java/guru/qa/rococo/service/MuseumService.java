package guru.qa.rococo.service;

import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.MuseumJson;
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
public class MuseumService {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumService.class);  //TODO: доп обработка

    private final MuseumRepository museumRepository;

    @Autowired
    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull Page<MuseumJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
        Page<MuseumEntity> museumEntities = (title == null)
                ? museumRepository.findAll(pageable)
                : museumRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        return museumEntities.map(MuseumJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public @Nonnull MuseumJson findMuseumById(@Nonnull String id) {
        return MuseumJson.fromEntity(
                museumRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new NotFoundException("Artist not found with id: " + id)
                        )
        );
    }

    @Transactional
    public @Nonnull MuseumJson addMuseum(@Nonnull MuseumJson museum) {
        MuseumEntity museumEntity = new MuseumEntity();
        museumEntity.setTitle(museum.getTitle());
        museumEntity.setDescription(museum.getDescription());
        museumEntity.setPhoto(museum.getPhoto() != null ? museum.getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        museumEntity.setGeoId(museum.getGeoId());

        MuseumEntity saved = museumRepository.save(museumEntity);
        return MuseumJson.fromEntity(saved);
    }

    @Transactional
    public @Nonnull MuseumJson updateMuseum(@Nonnull MuseumJson museum) {
        Optional<MuseumEntity> museumById = museumRepository.findById(museum.getId());
        if (museumById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist not found with id: " + museum.getId());
        } else {
            MuseumEntity museumEntity = museumById.get();
            museumEntity.setTitle(museum.getTitle());
            museumEntity.setDescription(museum.getDescription());
            museumEntity.setPhoto(museum.getPhoto() != null ? museum.getPhoto().getBytes(StandardCharsets.UTF_8) : null);
            museumEntity.setGeoId(museum.getGeoId());

            return MuseumJson.fromEntity(museumRepository.save(museumEntity));
        }
    }
}

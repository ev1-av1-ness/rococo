package guru.qa.rococo.service;

import guru.qa.rococo.data.PaintingEntity;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PaintingService {

    private static final Logger LOG = LoggerFactory.getLogger(PaintingService.class);

    private final PaintingRepository paintingRepository;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull Page<PaintingJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
        Page<PaintingEntity> paintingEntities = (title == null)
                ? paintingRepository.findAll(pageable)
                : paintingRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        return paintingEntities.map(PaintingJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public @Nonnull List<PaintingJson> getAllByIds(@Nonnull List<String> ids) {
        List<UUID> uuidsIds = ids.stream()
                .map(UUID::fromString)
                .toList();
        return paintingRepository.findAllByIds(uuidsIds)
                .stream()
                .map(PaintingJson::fromEntity)
                .toList();
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

    public @Nonnull PaintingJson addPainting(@Nonnull PaintingJson painting) {
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(painting.getTitle());
        paintingEntity.setDescription(painting.getDescription());
        paintingEntity.setContent(painting.getContent() != null ? painting.getContent().getBytes(StandardCharsets.UTF_8) : null);
        paintingEntity.setMuseumId(painting.getMuseumId());
        paintingEntity.setArtistId(painting.getArtistId());

        PaintingEntity saved = paintingRepository.save(paintingEntity);
        return PaintingJson.fromEntity(saved);
    }

    public @Nonnull PaintingJson updatePainting(@Nonnull PaintingJson painting) {
        Optional<PaintingEntity> paintingById = paintingRepository.findById(painting.getId());
        if (paintingById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Painting not found with id: " + painting.getId());
        } else {
            PaintingEntity paintingEntity = paintingById.get();
            paintingEntity.setTitle(painting.getTitle());
            paintingEntity.setContent(painting.getContent() != null ? painting.getContent().getBytes(StandardCharsets.UTF_8) : null);
            paintingEntity.setDescription(painting.getDescription());
            paintingEntity.setMuseumId(painting.getMuseumId());
            paintingEntity.setArtistId(painting.getArtistId());

            return PaintingJson.fromEntity(paintingRepository.save(paintingEntity));
        }
    }
}

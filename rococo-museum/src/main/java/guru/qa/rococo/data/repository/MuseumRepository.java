package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.MuseumEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {
    @Nonnull
    Page<MuseumEntity> findAllByTitleContainsIgnoreCase(@Nonnull String name,
                                                        @Nonnull Pageable pageable);
}

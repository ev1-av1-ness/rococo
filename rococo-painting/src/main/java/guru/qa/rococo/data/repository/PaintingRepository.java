package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.PaintingEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaintingRepository extends JpaRepository <PaintingEntity, UUID> {
    @Nonnull
    Page<PaintingEntity> findAllByNameContainsIgnoreCase(@Nonnull String name,
                                                         @Nonnull Pageable pageable);
}

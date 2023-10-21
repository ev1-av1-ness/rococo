package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.PaintingEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository <PaintingEntity, UUID> {
    @Nonnull
    List<PaintingEntity> findByTitleContaining(@Nonnull String title);
}

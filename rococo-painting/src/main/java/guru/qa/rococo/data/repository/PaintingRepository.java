package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.PaintingEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository <PaintingEntity, UUID> {
    @Nonnull
    Page<PaintingEntity> findAllByTitleContainsIgnoreCase(@Nonnull String title,
                                                         @Nonnull Pageable pageable);
    @Nonnull
    Page<PaintingEntity> findByArtistId(@Nonnull UUID artistId,
                                        @Nonnull Pageable pageable);

    @Query("SELECT e FROM PaintingEntity e WHERE e.id IN :ids")
    List<PaintingEntity> findAllByIds(@Param("ids") List<UUID> ids);
}

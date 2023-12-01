package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.MuseumEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {
    @Nonnull
    Page<MuseumEntity> findAllByTitleContainsIgnoreCase(@Nonnull String name,
                                                        @Nonnull Pageable pageable);

    @Query("SELECT e FROM MuseumEntity e WHERE e.id IN :ids")
    List<MuseumEntity> findAllByIds(@Param("ids") List<String> ids);
}

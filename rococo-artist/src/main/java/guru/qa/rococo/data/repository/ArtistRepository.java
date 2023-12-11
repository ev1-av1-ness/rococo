package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.ArtistEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {
    @Nonnull
    Page<ArtistEntity> findAllByNameContainsIgnoreCase(@Nonnull String name,
                                                       @Nonnull Pageable pageable);

    @Query("SELECT a FROM ArtistEntity a WHERE a.id IN :ids")
    List<ArtistEntity> findAllByIds(@Param("ids") List<UUID> ids);
}

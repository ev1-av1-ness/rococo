package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.ArtistEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {

    @Nonnull
    Page<ArtistEntity> findAllByNameContainsIgnoreCase(
            @Nonnull String name,
            @Nonnull Pageable pageable
    );

//    @Nullable
//    ArtistEntity findById(@Nonnull String id);
}

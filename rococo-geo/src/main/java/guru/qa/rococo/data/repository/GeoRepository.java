package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.GeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {

    @Query("select g from GeoEntity g where g.city = :city")
    Optional<GeoEntity> findByCity(@Param("city") String city);

    @Query("SELECT e FROM GeoEntity e WHERE e.id IN :ids")
    List<GeoEntity> findAllByIds(@Param("ids") List<UUID> ids);
}

package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.GeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {

    @Query("select g from GeoEntity g where g.city = :city")
    Optional<GeoEntity> findByCity(@Param("city") String city);
}

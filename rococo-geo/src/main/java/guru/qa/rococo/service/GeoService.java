package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.GeoEntity;
import guru.qa.rococo.data.repository.GeoRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class GeoService {
    private final GeoRepository geoRepository;

    @Autowired
    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull Page<CountryJson> getAll(@Nonnull Pageable pageable) {
        Page<CountryEntity> countryEntities = geoRepository.findAll(pageable).map(GeoEntity::getCountry);
        return countryEntities.map(CountryJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public @Nonnull GeoJson findGeoById(@Nonnull String id) {
        return GeoJson.fromEntity(
                geoRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new NotFoundException("Geo not found with id: " + id)
                        )
        );
    }
}

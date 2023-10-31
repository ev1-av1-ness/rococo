package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.repository.GeoRepository;
import guru.qa.rococo.model.CountryJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GeoService {
    private final GeoRepository geoRepository;

    @Autowired
    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull Page<CountryJson> getAll(@Nonnull Pageable pageable) {
        Page<CountryEntity> museumEntities = geoRepository.findAll(pageable);
        return museumEntities.map(CountryJson::fromEntity);
    }
}

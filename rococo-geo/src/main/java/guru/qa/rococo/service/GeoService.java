package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.repository.GeoRepository;
import guru.qa.rococo.model.CountryJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeoService {
    private final GeoRepository geoRepository;

    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    public List<CountryJson> getCountries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CountryEntity> countryPage = geoRepository.findAll(pageable);
        return countryPage
                .stream()
                .map(CountryJson::fromEntity)
                .collect(Collectors.toList());
    }
}

package guru.qa.rococo.service;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.GeoEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.MuseumJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class MuseumService {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumService.class);  //TODO: доп обработка

    private final MuseumRepository museumRepository;

    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    public Page<MuseumJson> getMuseums(Pageable pageable) {
        Page<MuseumEntity> artistEntities = museumRepository.findAll(pageable);
        return artistEntities.map(MuseumJson::fromEntity);
    }

    public MuseumEntity getMuseumById(String id) {
        return museumRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Museum not found with id: " + id));
    }

    public MuseumEntity createMuseum(@NonNull MuseumJson museumJson) {
        MuseumEntity museumEntity = new MuseumEntity();
        museumEntity.setTitle(museumJson.getTitle());
        museumEntity.setDescription(museumJson.getDescription());
        museumEntity.setPhoto(museumJson.getPhoto() != null ? museumJson.getPhoto().getBytes(StandardCharsets.UTF_8) : null);

        CountryEntity country = new CountryEntity();
        country.setName(museumJson.getGeo().getCountry().getName());

        GeoEntity geo = new GeoEntity();
        geo.setCity(museumJson.getGeo().getCity());
        geo.setCountry(country);

        museumEntity.setGeo(geo);
        return museumRepository.save(museumEntity);
    }
}

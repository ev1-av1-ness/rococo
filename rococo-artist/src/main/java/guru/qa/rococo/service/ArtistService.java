package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.ArtistJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Component
public class ArtistService {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @NonNull public ArtistEntity getArtistById(String id) {

        ArtistEntity artist = new ArtistEntity(); //TODO: реализовать

        if (artist == null) {
            throw new NotFoundException("Artist not found"); //TODO: добавить работу с экстеншенами
        }

        return artist;
    }
}

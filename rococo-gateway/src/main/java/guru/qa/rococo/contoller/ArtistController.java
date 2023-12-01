package guru.qa.rococo.contoller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.api.artist.ArtistClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistClient artistClient;

    @Autowired
    public ArtistController(ArtistClient artistClient) {
        this.artistClient = artistClient;
    }

    @GetMapping()
    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return artistClient.getAll(name, pageable);
    }

    @GetMapping("/{id}")
    public ArtistJson findArtistById(@PathVariable("id") String id) {
        return artistClient.findArtistById(id);
    }

    @PatchMapping()
    public ArtistJson updateArtist(@RequestBody ArtistJson artist) {
        return artistClient.updateArtist(artist);
    }

    @PostMapping()
    public ArtistJson addArtist(
            @RequestBody ArtistJson artist) {
        return artistClient.addArtist(artist);
    }
}

package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping()
    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return artistService.getAll(name, pageable);
    }

    @PatchMapping()
    public ArtistJson updateArtist(@RequestBody ArtistJson artist) {
        return artistService.updateArtist(artist);
    }

    @GetMapping("/{id}")
    public ArtistJson findArtistById(@PathVariable("id") String id) {
        return artistService.findArtistById(id);
    }

    @PostMapping()
    public ArtistJson addArtist(@RequestBody ArtistJson artist) {
        return artistService.addArtist(artist);
    }

}

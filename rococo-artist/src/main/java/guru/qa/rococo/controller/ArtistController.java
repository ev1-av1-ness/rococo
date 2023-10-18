package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/api/artist")
    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return artistService.getAll(name, pageable);
    }

//    @GetMapping("/api/artist/{id}")
//    public ResponseEntity<ArtistJson> getArtist(@PathVariable String id) {
//        ArtistJson artist = artistService.getArtistById(id);
//        return ResponseEntity.ok(artist);
//    }

//    @PostMapping("")
//    public <ArtistJson> updateUserInfo(@RequestBody ArtistJson artist) {
//        return artistService.update(artist);
//    }

}

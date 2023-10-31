package guru.qa.rococo.controller;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping()
    public ResponseEntity<Page<ArtistJson>> getArtists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "18") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArtistJson> artists = artistService.getArtists(pageable);
        return ResponseEntity.ok(artists);
    }

    //patch artist
    //get search by title

    @GetMapping("/{id}")
    public ResponseEntity<ArtistEntity> getArtistById(@PathVariable String id) {
        ArtistEntity artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping()
    public ResponseEntity<ArtistEntity> createArtist(@RequestBody ArtistJson artistJson) {
        ArtistEntity createdArtist = artistService.createArtist(artistJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArtist);
    }

}

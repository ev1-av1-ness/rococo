package guru.qa.rococo.controller;

import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/museum")
public class MuseumController {

    private final MuseumService museumService;

    @Autowired
    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @GetMapping()
    public ResponseEntity<Page<MuseumJson>> getMuseums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MuseumJson> artists = museumService.getMuseums(pageable);
        return ResponseEntity.ok(artists);
    }

    //patch museum
    //get search by title

    @GetMapping("/{id}")
    public ResponseEntity<MuseumEntity> getMuseumById(@PathVariable String id) {
        MuseumEntity museum = museumService.getMuseumById(id);
        return ResponseEntity.ok(museum);
    }

    @PostMapping()
    public ResponseEntity<MuseumEntity> createMuseum(@RequestBody MuseumJson museumJson) {
        MuseumEntity museum = museumService.createMuseum(museumJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(museum);
    }
}

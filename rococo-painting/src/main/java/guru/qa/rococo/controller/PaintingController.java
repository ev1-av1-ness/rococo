package guru.qa.rococo.controller;

import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/painting")
public class PaintingController {
    private final PaintingService paintingService;

    @Autowired
    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping()
    public ResponseEntity<Page<PaintingJson>> getPaintings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaintingJson> paintings = paintingService.getPaintings(pageable);
        return ResponseEntity.ok(paintings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaintingEntity> getPaintingById(@PathVariable String id) {
        PaintingEntity painting = paintingService.getPaintingById(id);
        return ResponseEntity.ok(painting);
    }

    @PostMapping()
    public ResponseEntity<PaintingEntity> createPainting(@RequestBody PaintingJson paintingJson) {
        PaintingEntity painting = paintingService.createPainting(paintingJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(painting);
    }
}

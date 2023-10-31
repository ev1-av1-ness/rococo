package guru.qa.rococo.controller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/painting")
public class PaintingController {
    private final PaintingService paintingService;

    @Autowired
    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping
    public Page<PaintingJson> getPaintings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return paintingService.getPaintings(pageable);
    }

    @PatchMapping
    public PaintingJson changePainting(@RequestBody PaintingJson painting) {
        return paintingService.changePainting(painting);
    }

    @GetMapping("/{id}")
    public PaintingJson getPaintingById(@PathVariable String id) {
        return paintingService.getPaintingById(id);
    }

    @GetMapping
    public Page<PaintingJson> getPaintingsByTitle(@RequestParam(required = false) String title,
                                                  @PageableDefault Pageable pageable) {
        return paintingService.getPaintingsByTitle(title, pageable); //TODO: статус код и ошибка обработчик
    }

    @PostMapping
    public PaintingJson createPainting(@RequestBody PaintingJson painting) {
        return paintingService.createPainting(painting);
    }
}

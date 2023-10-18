package guru.qa.rococo.controller;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.MuseumService;
import guru.qa.rococo.service.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaintingController {
    private final PaintingService paintingService;

    @Autowired
    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping("/api/painting")
    public Page<PaintingJson> getAll(@RequestParam(required = false) String name,
                                     @PageableDefault Pageable pageable) {
        return paintingService.getAll(name, pageable);
    }
}

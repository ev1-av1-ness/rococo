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
    public Page<PaintingJson> getAll(@RequestParam(required = false) String name,
                                     @PageableDefault Pageable pageable) {
        return paintingService.getAll(name, pageable);
    }

    @PatchMapping
    public PaintingJson updatePainting(@RequestBody PaintingJson painting) {
        return paintingService.changePainting(painting);
    }

    //TODO:
    //pageable
//    http://127.0.0.1:8080/api/painting/author/5a486b2f-c361-459e-bd3f-60692a635ea9?size=9&page=0
//    Request Method:
//    GET
//    @GetMapping("/author/{id}")
//    public Page<PaintingJson> findPaintingByAuthorId(@PathVariable("id") String id,
//                                     @PageableDefault Pageable pageable) {
//        return paintingService.findPaintingByAuthorId(id, pageable);
//    }


    @GetMapping("/{id}")
    public PaintingJson findPaintingById(@PathVariable("id") String id) {
        return paintingService.findPaintingById(id);
    }

    @PostMapping
    public PaintingJson addPainting(@RequestBody PaintingJson painting) {
        return paintingService.createPainting(painting);
    }
}

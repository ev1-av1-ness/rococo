package guru.qa.rococo.contoller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.DataAggergator;
import guru.qa.rococo.service.api.painting.PaintingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/painting")
public class PaintingController {
    private static final Logger LOG = LoggerFactory.getLogger(PaintingController.class);

    private final PaintingClient paintingClient;
    private final DataAggergator dataAggregator;

    @Autowired
    public PaintingController(PaintingClient paintingClient, DataAggergator dataAggregator) {
        this.paintingClient = paintingClient;
        this.dataAggregator = dataAggregator;
    }

    @GetMapping()
    public Page<PaintingJson> getAll(@RequestParam(required = false) String name,
                                     @PageableDefault Pageable pageable) {
        Page<PaintingJson> paintingJsonPage = paintingClient.getAll(name, pageable);
        return dataAggregator.enrichPaintings(paintingJsonPage);
    }

    @GetMapping("/{id}")
    public PaintingJson findPaintingById(@PathVariable("id") String id) {
        return dataAggregator.getPainting(id);
    }

    @GetMapping("/author/{artistId}")
    public Page<PaintingJson> findPaintingByAuthorId(@PathVariable("artistId") String artistId,
                                                     @PageableDefault Pageable pageable) {
        return paintingClient.findPaintingByAuthorId(artistId, pageable);
    }

    @PatchMapping()
    public PaintingJson updatePainting(@RequestBody PaintingJson painting) {
        return paintingClient.updatePainting(painting);
    }

    @PostMapping()
    public PaintingJson addPainting(
            @RequestBody PaintingJson painting) {
        return paintingClient.addPainting(painting);
    }
}
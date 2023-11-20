package guru.qa.rococo.contoller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.DataAggergator;
import guru.qa.rococo.service.api.artist.ArtistClient;
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
    private final ArtistClient artistClient;

    @Autowired
    public PaintingController(PaintingClient paintingClient, DataAggergator dataAggregator, ArtistClient artistClient) {
        this.paintingClient = paintingClient;
        this.dataAggregator = dataAggregator;
        this.artistClient = artistClient;
    }

    @GetMapping()
    public Page<PaintingJson> getAll(@RequestParam(required = false) String title,
                                     @PageableDefault Pageable pageable) {
        Page<PaintingJson> paintingJsonPage = paintingClient.getAll(title, pageable);
        return dataAggregator.enrichPaintings(paintingJsonPage);
    }

    @GetMapping("/{id}")
    public PaintingJson findPaintingById(@PathVariable("id") String id) {
        return dataAggregator.getPainting(id);
    }

    @GetMapping("/author/{artistId}")
    public Page<PaintingJson> findPaintingByAuthorId(@PathVariable("artistId") String artistId,
                                                     @PageableDefault Pageable pageable) {
        Page<PaintingJson> paintingJsonPage = paintingClient.findPaintingByAuthorId(artistId, pageable);
        return dataAggregator.enrichPaintings(paintingJsonPage);
    }

    @PatchMapping()
    public PaintingJson updatePainting(@RequestBody PaintingJson painting) {
        painting.setArtistId(painting.getArtist().getId());
        painting.setMuseumId(painting.getMuseum().getId());
        return paintingClient.updatePainting(painting);
    }

    @PostMapping()
    public PaintingJson addPainting(
            @RequestBody PaintingJson painting) {
        painting.setArtistId(painting.getArtist().getId());
        painting.setMuseumId(painting.getMuseum().getId());
        return paintingClient.addPainting(painting);
    }
}
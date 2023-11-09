package guru.qa.rococo.contoller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.DataAggergator;
import guru.qa.rococo.service.api.painting.PaintingClient;
import guru.qa.rococo.service.api.painting.model.PaintingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PaintingController {
    private static final Logger LOG = LoggerFactory.getLogger(PaintingController.class);

    private final PaintingClient paintingClient;
    private final DataAggergator dataAggregator;

    @Autowired
    public PaintingController(PaintingClient paintingClient, DataAggergator dataAggergator) {
        this.paintingClient = paintingClient;
        this.dataAggregator = dataAggergator;
    }

    @GetMapping()
    public Page<PaintingJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        Page<PaintingDto> paintingDtoPage = paintingClient.getAll(name, pageable);
        List<PaintingJson> paintingJsonList = new ArrayList<>();

        for (PaintingDto paintingDto : paintingDtoPage.getContent()) {
            PaintingJson paintingJson = dataAggregator.getPainting(String.valueOf(paintingDto.getId()));
            paintingJsonList.add(paintingJson);
        }

        return new PageImpl<>(paintingJsonList, paintingDtoPage.getPageable(), paintingDtoPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public PaintingJson findPaintingById(@PathVariable("id") String id) {
        return dataAggregator.getPainting(id);
    }

    @GetMapping("/author/{artistId}")
    public Page<PaintingJson> findPaintingByAuthorId(@PathVariable("artistId") String artistId,
                                                     @PageableDefault Pageable pageable) {
        Page<PaintingDto> paintingDtoPage = paintingClient.findPaintingByAuthorId(artistId, pageable);
        List<PaintingJson> paintingJsonList = new ArrayList<>();

        for (PaintingDto paintingDto : paintingDtoPage.getContent()) {
            PaintingJson paintingJson = dataAggregator.getPainting(String.valueOf(paintingDto.getId()));
            paintingJsonList.add(paintingJson);
        }

        return new PageImpl<>(paintingJsonList, paintingDtoPage.getPageable(), paintingDtoPage.getTotalElements());
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

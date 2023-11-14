package guru.qa.rococo.contoller;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.DataAggergator;
import guru.qa.rococo.service.api.museum.MuseumClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/museum")
public class MuseumController {
    private static final Logger LOG = LoggerFactory.getLogger(MuseumController.class);
    private final MuseumClient museumClient;
    private final DataAggergator dataAggregator;

    @Autowired
    public MuseumController(MuseumClient museumClient, DataAggergator dataAggregator) {
        this.museumClient = museumClient;
        this.dataAggregator = dataAggregator;
    }

    @GetMapping()
    public Page<MuseumJson> getAll(@RequestParam(required = false) String title,
                                   @PageableDefault Pageable pageable) {
        Page<MuseumJson> museumJsonPage = museumClient.getAll(title, pageable);
        return dataAggregator.enrichMuseums(museumJsonPage);
    }

    @GetMapping("/{id}")
    public MuseumJson findMuseumById(@PathVariable("id") String id) {
        return dataAggregator.getMuseum(id);
    }

    @PatchMapping()
    public MuseumJson updateMuseum(@RequestBody MuseumJson museum) {
        return museumClient.updateMuseum(museum);
    }

    @PostMapping()
    public MuseumJson addMuseum(
            @RequestBody MuseumJson museum) {
        return museumClient.addMuseum(museum);
    }
}

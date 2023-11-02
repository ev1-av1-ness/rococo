package guru.qa.rococo.contoller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.api.MuseumClient;
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

    @Autowired
    public MuseumController(MuseumClient museumClient) {
        this.museumClient = museumClient;
    }

    @GetMapping()
    public Page<MuseumJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return museumClient.getAll(name, pageable);
    }

    @GetMapping("/{id}")
    public MuseumJson findMuseumById(@PathVariable("id") String id) {
        return museumClient.findMuseumById(id);
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

package guru.qa.rococo.contoller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.api.ArtistClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistClient artistClient;

    @Autowired
    public ArtistController(ArtistClient artistClient) {
        this.artistClient = artistClient;
    }

//    @GetMapping()
//    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
//                                   @PageableDefault Pageable pageable) {
//        return artistService.getAll(name, pageable);
//    }
}

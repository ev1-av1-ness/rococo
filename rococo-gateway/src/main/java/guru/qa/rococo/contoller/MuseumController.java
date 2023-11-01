package guru.qa.rococo.contoller;

import guru.qa.rococo.service.api.MuseumClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/museum")
public class MuseumController {
    private static final Logger LOG = LoggerFactory.getLogger(MuseumController.class);
    private final MuseumClient museumClient;

    @Autowired
    public MuseumController(MuseumClient museumClient) {
        this.museumClient = museumClient;
    }
}

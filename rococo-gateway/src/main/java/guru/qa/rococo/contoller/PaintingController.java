package guru.qa.rococo.contoller;

import guru.qa.rococo.service.api.PaintingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaintingController {
    private static final Logger LOG = LoggerFactory.getLogger(PaintingController.class);

    private final PaintingClient paintingClient;

    @Autowired
    public PaintingController(PaintingClient paintingClient) {
        this.paintingClient = paintingClient;
    }
}

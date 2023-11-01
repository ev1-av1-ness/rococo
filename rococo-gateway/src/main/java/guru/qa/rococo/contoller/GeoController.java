package guru.qa.rococo.contoller;

import guru.qa.rococo.service.api.GeoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/country")
public class GeoController {
    private static final Logger LOG = LoggerFactory.getLogger(GeoController.class);
    private final GeoClient geoClient;

    @Autowired
    public GeoController(GeoClient geoClient) {
        this.geoClient = geoClient;
    }
}

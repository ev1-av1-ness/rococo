package guru.qa.rococo.contoller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.service.api.geo.GeoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public Page<CountryJson> getAll(@PageableDefault Pageable pageable) {
        return geoClient.getAll(pageable);
    }
}

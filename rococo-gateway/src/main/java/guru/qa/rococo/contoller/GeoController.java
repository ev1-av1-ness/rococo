package guru.qa.rococo.contoller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.service.DataAggergator;
import guru.qa.rococo.service.api.geo.GeoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeoController {
    private static final Logger LOG = LoggerFactory.getLogger(GeoController.class);
    private final GeoClient geoClient;
    private final DataAggergator dataAggregator;

    @Autowired
    public GeoController(GeoClient geoClient, DataAggergator dataAggregator) {
        this.geoClient = geoClient;
        this.dataAggregator = dataAggregator;
    }

    @GetMapping("/api/country")
    public Page<CountryJson> getAll(@PageableDefault Pageable pageable) {
        return geoClient.getAll(pageable);
    }

    @GetMapping("/api/geo/{id}")
    public GeoJson findGeoById(@PathVariable("id") String id) {
        return dataAggregator.getGeo(id);
    }
}

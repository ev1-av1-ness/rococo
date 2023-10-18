package guru.qa.rococo.controller;

import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeoController {
    private final GeoService geoService;

    @Autowired
    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/api/country")
    public List<CountryJson> getAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size) {
        return geoService.getCountries(page, size);
    }

    //TODO: Update geo (add city)
    //посмотреть rangiffler
}

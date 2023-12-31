package guru.qa.rococo.controller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Page<CountryJson> getAll(@PageableDefault Pageable pageable) {
        return geoService.getAll(pageable);
    }

    @GetMapping("/api/geo/all")
    public List<GeoJson> getAllByIds(@RequestParam("ids") List<String> ids) {
        return geoService.getAllByIds(ids);
    }

    @GetMapping("/api/geo/{id}")
    public GeoJson findGeoById(@PathVariable("id") String id) {
        return geoService.findGeoById(id);
    }

    @GetMapping("/api/geo/city/{city}")
    public GeoJson findGeoByCity(@PathVariable("city") String city) {
        return geoService.findGeoByCity(city);
    }

    @GetMapping("/api/geo")
    public Page<GeoJson> getAllGeo(@PageableDefault Pageable pageable) {
        return geoService.getAllGeo(pageable);
    }
}

package guru.qa.rococo.contoller;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.DataAggergator;
import guru.qa.rococo.service.api.museum.MuseumClient;
import guru.qa.rococo.service.api.museum.model.MuseumDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/museum")
public class MuseumController {
    private static final Logger LOG = LoggerFactory.getLogger(MuseumController.class);
    private final MuseumClient museumClient;
    private final DataAggergator dataAggregator;

    @Autowired
    public MuseumController(MuseumClient museumClient, DataAggergator dataAggergator) {
        this.museumClient = museumClient;
        this.dataAggregator = dataAggergator;
    }

    @GetMapping()
    public Page<MuseumJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        Page<MuseumDto> museumDtoPage = museumClient.getAll(name, pageable);
        List<MuseumJson> museumJsonList = new ArrayList<>();

        for (MuseumDto museumDto : museumDtoPage.getContent()) {
            MuseumJson museumJson = dataAggregator.getMuseum(String.valueOf(museumDto.getId()));
            museumJsonList.add(museumJson);
        }

        return new PageImpl<>(museumJsonList, museumDtoPage.getPageable(), museumDtoPage.getTotalElements());
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

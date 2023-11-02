package guru.qa.rococo.service.api;

import guru.qa.rococo.model.CountryJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class GeoClient {
    private final WebClient webClient;
    private final String rococoGeoBaseUri;

    @Autowired
    public GeoClient(WebClient webClient,
                        @Value("${rococo-geo.base-uri}") String rococoGeoBaseUri) {
        this.webClient = webClient;
        this.rococoGeoBaseUri = rococoGeoBaseUri;
    }

    public @Nonnull
    Page<CountryJson> getAll(@Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoGeoBaseUri + "/api/country").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(CountryJson.class)
                .collectList()
                .map(countryList -> createPage(countryList, pageable)).block();

    }

    private Page<CountryJson> createPage(List<CountryJson> countryList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), countryList.size());
        return new PageImpl<>(countryList.subList(start, end), pageable, countryList.size());
    }
}

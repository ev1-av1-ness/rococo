package guru.qa.rococo.service.api.geo;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.service.api.CustomPageImpl;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

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
                .bodyToMono(new ParameterizedTypeReference<CustomPageImpl<CountryJson>>() {})
                .block();
    }

    public @Nonnull
    GeoJson findGeoById(@Nonnull String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoGeoBaseUri + "/api/geo").path("/{id}").build(id);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GeoJson.class)
                .block();
    }

    public @Nonnull
    GeoJson findGeoByCity(@Nonnull String city) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoGeoBaseUri + "/api/geo/city").path("/{city}")
                .encode(UTF_8)
                .build(city);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GeoJson.class)
                .block();
    }
}

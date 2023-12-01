package guru.qa.rococo.service.api.painting;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.api.CustomPageImpl;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class PaintingClient {
    private final WebClient webClient;
    private final String rococoPaintingBaseUri;

    @Autowired
    public PaintingClient(WebClient webClient,
                        @Value("${rococo-painting.base-uri}") String rococoPaintingBaseUri) {
        this.webClient = webClient;
        this.rococoPaintingBaseUri = rococoPaintingBaseUri;
    }

    public @Nonnull
    PaintingJson updatePainting(@Nonnull PaintingJson painting) {
        return webClient.patch()
                .uri(rococoPaintingBaseUri + "/api/painting")
                .body(Mono.just(painting), PaintingJson.class)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }

    public @Nonnull
    PaintingJson addPainting(@Nonnull PaintingJson painting) {
        return webClient.post()
                .uri(rococoPaintingBaseUri + "/api/painting")
                .body(Mono.just(painting), PaintingJson.class)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }

    public @Nonnull
    Page<PaintingJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (title != null) {
            params.add("title", title);
        }
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting").queryParams(params)
                .encode(UTF_8)
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CustomPageImpl<PaintingJson>>() {})
                .block();

    }

    public @Nonnull
    PaintingJson findPaintingById(@Nonnull String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting").path("/{id}").build(id);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }

    public @Nonnull
    Page<PaintingJson> findPaintingByAuthorId(@Nonnull String artistId, @Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting/author").path("/{artistId}")
                .queryParams(params)
                .buildAndExpand(artistId).toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CustomPageImpl<PaintingJson>>() {})
                .block();
    }
}

package guru.qa.rococo.service.api;

import guru.qa.rococo.model.PaintingJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

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
    Page<PaintingJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (name != null) {
            params.add("title", name);
        }
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(PaintingJson.class)
                .collectList()
                .map(paintingList -> createPage(paintingList, pageable)).block();

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

    private Page<PaintingJson> createPage(List<PaintingJson> paintingList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), paintingList.size());
        return new PageImpl<>(paintingList.subList(start, end), pageable, paintingList.size());
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
                .bodyToFlux(PaintingJson.class)
                .collectList()
                .map(paintingList -> createPage(paintingList, pageable)).block();
    }
}

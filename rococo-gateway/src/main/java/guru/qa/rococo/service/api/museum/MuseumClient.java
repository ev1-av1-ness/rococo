package guru.qa.rococo.service.api.museum;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.api.CustomPageImpl;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
public class MuseumClient {
    private final WebClient webClient;
    private final String rococoMuseumBaseUri;

    @Autowired
    public MuseumClient(WebClient webClient,
                        @Value("${rococo-museum.base-uri}") String rococoMuseumBaseUri) {
        this.webClient = webClient;
        this.rococoMuseumBaseUri = rococoMuseumBaseUri;
    }

    public @Nonnull
    MuseumJson updateMuseum(@Nonnull MuseumJson museum) {
        return webClient.patch()
                .uri(rococoMuseumBaseUri + "/api/museum")
                .body(Mono.just(museum), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public @Nonnull
    MuseumJson addMuseum(@Nonnull MuseumJson museum) {
        return webClient.post()
                .uri(rococoMuseumBaseUri + "/api/museum")
                .body(Mono.just(museum), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public @Nonnull
    Page<MuseumJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (title != null) {
            params.add("title", title);
        }
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/api/museum").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CustomPageImpl<MuseumJson>>() {})
                .block();
    }

    public @Nonnull
    MuseumJson findMuseumById(@Nonnull String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/api/museum").path("/{id}").build(id);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }
}

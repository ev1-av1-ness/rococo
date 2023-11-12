package guru.qa.rococo.service.api.artist;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.CountryJson;
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
public class ArtistClient {
    private final WebClient webClient;
    private final String rococoArtistBaseUri;

    @Autowired
    public ArtistClient(WebClient webClient,
                          @Value("${rococo-artist.base-uri}") String rococoArtistBaseUri) {
        this.webClient = webClient;
        this.rococoArtistBaseUri = rococoArtistBaseUri;
    }

    public @Nonnull
    ArtistJson updateArtist(@Nonnull ArtistJson artist) {
        return webClient.patch()
                .uri(rococoArtistBaseUri + "/api/artist")
                .body(Mono.just(artist), ArtistJson.class)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }

    public @Nonnull
    ArtistJson addArtist(@Nonnull ArtistJson artist) {
        return webClient.post()
                .uri(rococoArtistBaseUri + "/api/artist")
                .body(Mono.just(artist), ArtistJson.class)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }

    public @Nonnull
    Page<ArtistJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (name != null) {
            params.add("name", name);
        }
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/api/artist").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CustomPageImpl<ArtistJson>>() {})
                .block();
    }

    public @Nonnull
    ArtistJson findArtistById(@Nonnull String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/api/artist").path("/{id}").build(id);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }
}

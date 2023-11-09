package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.Date;

public record SessionJson(@JsonProperty("username") String username,
                          @JsonProperty("issuedAt") Date issuedAt,
                          @JsonProperty("expiresAt") Date expiresAt) {
    public static @NonNull SessionJson empty() {
        return new SessionJson(null, null, null);
    }
}

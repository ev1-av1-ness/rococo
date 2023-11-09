package guru.qa.rococo.contoller;

import guru.qa.rococo.model.SessionJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("api/session")
public class SessionController {
    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    @GetMapping()
    public SessionJson session(@AuthenticationPrincipal Jwt principal) {
        if (principal != null) {
            return new SessionJson(
                    principal.getClaim("sub"),
                    Date.from(requireNonNull(principal.getIssuedAt())),
                    Date.from(requireNonNull(principal.getExpiresAt()))
            );
        } else {
            return SessionJson.empty();
        }
    }

}

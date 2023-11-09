package guru.qa.rococo.contoller;

import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.api.userdata.UserDataClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserDataClient userDataClient;

    @Autowired
    public UserController(UserDataClient userDataClient) {
        this.userDataClient = userDataClient;
    }

    @PatchMapping()
    public UserJson updateUserInfo(@AuthenticationPrincipal Jwt principal,
                                   @Validated @RequestBody UserJson user) {
        String username = principal.getClaim("sub");
        user.setUsername(username);
        return userDataClient.updateUserInfo(user);
    }

    @GetMapping()
    public UserJson currentUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userDataClient.currentUser(username);
    }
}

package guru.qa.rococo.controller;

import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserDataService userDataService;

    @Autowired
    public UserController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PatchMapping("/api/user")
    public UserJson updateUserInfo(@RequestBody UserJson user) {
        return userDataService.update(user);
    }

    @GetMapping("/api/user")
    public UserJson currentUser(@RequestParam String username) {
        return userDataService.getCurrentUser(username);
    }

}

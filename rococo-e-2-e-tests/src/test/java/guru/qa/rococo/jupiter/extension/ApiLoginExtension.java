package guru.qa.rococo.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.rococo.api.AuthServiceClient;
import guru.qa.rococo.api.context.CookieContext;
import guru.qa.rococo.api.context.SessionStorageContext;
import guru.qa.rococo.config.Config;
import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.GenerateUser;
import guru.qa.rococo.jupiter.annotation.GeneratedUser;
import guru.qa.rococo.model.UserJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import java.io.IOException;

import static guru.qa.rococo.jupiter.extension.CreateUserExtension.NESTED;

public class ApiLoginExtension implements BeforeEachCallback {

    private final AuthServiceClient authServiceClient = new AuthServiceClient();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        ApiLogin annotation = extensionContext.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (annotation != null) {
            GenerateUser user = annotation.user();
            if (user.handleAnnotation()) {
                UserJson createdUser = extensionContext.getStore(NESTED).get(
                        GeneratedUser.Selector.NESTED,
                        UserJson.class
                );
                doLogin(createdUser.getUsername(), createdUser.getPassword());
            } else {
                doLogin(annotation.username(), annotation.password());
            }
        }
    }

    private void doLogin(String username, String password) {
        SessionStorageContext sessionStorageContext = SessionStorageContext.getInstance();
        sessionStorageContext.init();

        try {
            authServiceClient.doLogin(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Selenide.open(Config.getInstance().rococoFrontUrl());
        Selenide.sessionStorage().setItem("codeChallenge", sessionStorageContext.getCodeChallenge());
        Selenide.sessionStorage().setItem("id_token", sessionStorageContext.getToken());
        Selenide.sessionStorage().setItem("codeVerifier", sessionStorageContext.getCodeVerifier());
        Cookie jsessionIdCookie = new Cookie("JSESSIONID", CookieContext.getInstance().getJSessionIdCookieValue());
        WebDriverRunner.getWebDriver().manage().addCookie(jsessionIdCookie);
    }
}

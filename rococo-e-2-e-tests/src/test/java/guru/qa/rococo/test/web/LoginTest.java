package guru.qa.rococo.test.web;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import guru.qa.rococo.db.model.auth.AuthUserEntity;
import guru.qa.rococo.db.model.auth.Authority;
import guru.qa.rococo.db.model.auth.AuthorityEntity;
import guru.qa.rococo.db.repository.UserRepository;
import guru.qa.rococo.db.repository.UserRepositoryHibernate;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8089)
public class LoginTest extends BaseWebTest {

    private static final String defaultPassword = "12345";
    private static String userdataUrl;

    private UserRepository userRepository = new UserRepositoryHibernate();
    private AuthUserEntity authUser;

    private WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration()
            .port(8089));

    static {
        String[] urlParts = CFG.rococoUserdataUrl().split(":");
        userdataUrl = urlParts[0];
    }

    private WireMock wiremock = new WireMock(
            userdataUrl,
            8089
    );

    @BeforeEach
    void createUser() {
        wiremock.register(get(urlPathEqualTo("/currentUser"))
                .withQueryParam("username", equalTo("valentin_30"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type", "Application/json")
                        .withBody("{\n" +
                                "      \"id\": \"229fc371-2821-4795-81a5-0b26d3cd417e\",\n" +
                                "      \"username\": \"valentin_30\",\n" +
                                "      \"firstname\": \"Valentin\",\n" +
                                "      \"surname\": null,\n" +
                                "      \"photo\": null\n" +
                                "    }")
                ));

        authUser = new AuthUserEntity();
        authUser.setUsername("valentin_30");
        authUser.setPassword(defaultPassword);
        authUser.setEnabled(true);
        authUser.setAccountNonExpired(true);
        authUser.setAccountNonLocked(true);
        authUser.setCredentialsNonExpired(true);
        authUser.setAuthorities(new ArrayList<>(Arrays.stream(Authority.values())
                .map(a -> {
                    AuthorityEntity ae = new AuthorityEntity();
                    ae.setAuthority(a);
                    ae.setUser(authUser);
                    return ae;
                }).toList()));

        userRepository.createUserForTest(authUser);
    }

    @AfterEach
    void deleteUser() {
        userRepository.removeAfterTest(authUser);
    }

    @Test
    @AllureId("954")
    void mainPageShouldBeVisibleAfterLogin() throws IOException {
        Selenide.open(CFG.rococoFrontUrl() + "/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(authUser.getUsername());
        $("input[name='password']").setValue(defaultPassword);
        $("button[type='submit']").click();
        $(".main-content__section-stats").should(visible);
    }

    @Test
    @AllureId("955")
    void wrongPassTest() {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(authUser.getUsername());
        $("input[name='password']").setValue("wrongpassword");
        $("button[type='submit']").click();
        $(".form__error").should(text("Bad credentials"));
    }

    @Test
    @AllureId("956")
    void registerUser() {
        Selenide.open("http://127.0.0.1:9000/register");
        $("input[name='username']").setValue(new Faker().name().username());
        $("input[name='password']").setValue(defaultPassword);
        $("input[name='passwordSubmit']").setValue(defaultPassword);
        $("button[type='submit']").click();
        $$(".form__paragraph").first().should(text("Congratulations! You've registered!"));
        $("a[href*='redirect']").should(interactable);
    }

    @Test
    @AllureId("957")
    void registerUserWithTheNotSamePass() {
        Selenide.open("http://127.0.0.1:9000/register");
        $("input[name='username']").setValue(new Faker().name().username());
        $("input[name='password']").setValue(defaultPassword);
        $("input[name='passwordSubmit']").setValue(new Faker().internet().password());
        $("button[type='submit']").click();
        $(".form__error").should(text("Passwords should be equal"));
    }

    @Test
    @AllureId("958")
    void registerUserWithTheSameName() {
        Selenide.open("http://127.0.0.1:9000/register");
        $("input[name='username']").setValue("barsik");
        $("input[name='password']").setValue(defaultPassword);
        $("input[name='passwordSubmit']").setValue(defaultPassword);
        $("button[type='submit']").click();
        $(".form__error").should(partialText("already exists"));
    }
}

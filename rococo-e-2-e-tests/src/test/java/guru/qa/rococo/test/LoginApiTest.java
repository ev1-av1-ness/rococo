package guru.qa.rococo.test;

import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.DBUser;
import guru.qa.rococo.test.web.BaseWebTest;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginApiTest extends BaseWebTest {

    @DBUser
    @ApiLogin
    @Test
    void mainPageShouldBeVisibleAfterLogin() {
        open(CFG.rococoFrontUrl() + "/main");
        $(".main-content__section-stats").shouldBe(visible);
    }
}

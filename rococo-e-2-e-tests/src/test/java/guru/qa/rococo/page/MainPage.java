package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public static final String pageUrl = "/welcome";

    private final SelenideElement loginBtn = $("a[href*='redirect']");
    private final SelenideElement registerBtn = $("a[href*='register']");

    public LoginPage goToLoginPage() {
        loginBtn.click();
        return new LoginPage();
    }
}
package guru.qa.rococo.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

public class DockerConfig implements Config {

    static final DockerConfig config = new DockerConfig();

    static {
        Configuration.remote = "http://selenoid:4444/wd/hub";
        Configuration.browserSize = "1980x1024";
        Configuration.browser = "chrome";
        Configuration.browserVersion = "110.0";
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--no-sandbox");
    }

    private DockerConfig() {
    }

    @Override
    public String databaseHost() {
        return "rococo-all";
    }

    @Override
    public String rococoFrontUrl() {
        return "http://client.rococo.dc";
    }

    @Override
    public String rococoAuthUrl() {
        return "http://auth.rococo.dc:9000";
    }

    @Override
    public String rococoUserdataUrl() {
        return "http://userdata.rococo.dc:8092";
    }

    @Override
    public String rococoGatewayUrl() {
        return "http://gateway.rococo.dc:8080";
    }

    @Override
    public String kafkaAddress() {
        return "kafka:9092";
    }
}

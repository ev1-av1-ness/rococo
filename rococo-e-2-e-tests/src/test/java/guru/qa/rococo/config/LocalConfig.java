package guru.qa.rococo.config;

import com.codeborne.selenide.Configuration;

public class LocalConfig implements Config {

    static final LocalConfig config = new LocalConfig();

    static {
        Configuration.browserSize = "1980x1024";
    }

    private LocalConfig() {
    }

    @Override
    public String databaseHost() {
        return "localhost";
    }

    @Override
    public String rococoFrontUrl() {
        return "http://127.0.0.1:3000";
    }

    @Override
    public String rococoAuthUrl() {
        return "http://127.0.0.1:9000";
    }

    @Override
    public String rococoUserdataUrl() {
        return "http://127.0.0.1:8092";
    }

    @Override
    public String rococoGatewayUrl() {
        return "http://127.0.0.1:8080";
    }

    @Override
    public String kafkaAddress() {
        return "localhost:9092";
    }
}

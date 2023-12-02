package guru.qa.rococo.config;

import java.util.List;

public interface Config {

    static Config getInstance() {
        if ("docker".equals(System.getProperty("test.env"))) {
            return DockerConfig.config;
        }
        return LocalConfig.config;
    }

    String databaseHost();

    String rococoFrontUrl();

    default String databaseUser() {
        return "root";
    }

    default String databasePassword() {
        return "secretdb";
    }

    default int databasePort() {
        return 3306;
    }

    String rococoAuthUrl();

    String rococoUserdataUrl();

    String rococoGatewayUrl();

    String kafkaAddress();

    default List<String> kafkaTopics() {
        return List.of("users");
    }
}

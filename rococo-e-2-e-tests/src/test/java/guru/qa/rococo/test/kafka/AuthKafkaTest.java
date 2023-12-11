package guru.qa.rococo.test.kafka;

import guru.qa.rococo.api.AuthServiceClient;
import guru.qa.rococo.kafka.KafkaConsumerService;
import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.util.FakerUtils;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AuthKafkaTest extends BaseKafkaTest {

    private AuthServiceClient authServiceClient = new AuthServiceClient();


    @AllureId("23536")
    @Test
    void authShouldProduceMessageAfterRegistration() throws IOException {
        final String username = FakerUtils.generateRandomUsername();
        final String password = FakerUtils.generateRandomPassword();

        int code = authServiceClient.doRegister(username, password);

        Assertions.assertEquals(201, code);

        UserJson message = KafkaConsumerService.getMessage(username);

        Assertions.assertNotNull(message);
    }
}

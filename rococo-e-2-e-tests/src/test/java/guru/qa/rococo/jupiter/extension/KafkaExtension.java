package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.kafka.KafkaConsumerService;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaExtension implements SuiteExtension {

    private static KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void beforeAllTests(ExtensionContext extensionContext) {
        executor.execute(kafkaConsumerService);
        executor.shutdown();
    }

    @Override
    public void afterAllTests() {
        kafkaConsumerService.shutdown();
    }
}

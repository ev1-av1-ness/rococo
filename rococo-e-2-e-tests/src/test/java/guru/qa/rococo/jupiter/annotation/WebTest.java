package guru.qa.rococo.jupiter.annotation;

import guru.qa.rococo.jupiter.extension.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({
        DbCreateUserExtension.class,
        ApiLoginExtension.class,
        ClearContextExtension.class,
        BrowserExtension.class,
        JpaExtension.class,
        AllureJunit5.class
})
public @interface WebTest {
}

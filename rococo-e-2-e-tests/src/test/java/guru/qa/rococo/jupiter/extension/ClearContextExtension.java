package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.api.context.CookieContext;
import guru.qa.rococo.api.context.SessionStorageContext;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ClearContextExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        SessionStorageContext.getInstance().clearContext();
        CookieContext.getInstance().clearContext();
    }
}

package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.dao.AuthUserDAO;
import guru.qa.rococo.db.dao.UserdataUserDAO;
import guru.qa.rococo.db.dao.impl.AuthUserDAOHibernate;
import guru.qa.rococo.jupiter.annotation.Dao;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class DaoExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if ((field.getType().isAssignableFrom(AuthUserDAO.class) || field.getType().isAssignableFrom(UserdataUserDAO.class))
                    && field.isAnnotationPresent(Dao.class)) {
                field.setAccessible(true);

                AuthUserDAO dao = new AuthUserDAOHibernate();

                field.set(testInstance, dao);
            }

        }

    }
}
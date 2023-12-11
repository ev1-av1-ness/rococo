package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.dao.impl.AuthUserDAOHibernate;
import guru.qa.rococo.db.dao.impl.UserdataUserDAOHibernate;

public class UserRepositoryHibernate extends AbstractUserRepository {
    public UserRepositoryHibernate() {
        super(new AuthUserDAOHibernate(), new UserdataUserDAOHibernate());
    }
}

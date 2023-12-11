package guru.qa.rococo.db.repository;


import guru.qa.rococo.db.model.auth.AuthUserEntity;

public interface UserRepository {
    void createUserForTest(AuthUserEntity user);

    void removeAfterTest(AuthUserEntity user);
}

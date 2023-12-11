package guru.qa.rococo.db.dao;

import guru.qa.rococo.db.model.userdata.UserDataUserEntity;

public interface UserdataUserDAO {

    int createUserInUserData(UserDataUserEntity user);

    void deleteUserInUserData(UserDataUserEntity userId);

    UserDataUserEntity getUserInUserDataByUsername(String username);
}

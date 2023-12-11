package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.model.auth.AuthUserEntity;
import guru.qa.rococo.db.model.auth.Authority;
import guru.qa.rococo.db.model.auth.AuthorityEntity;
import guru.qa.rococo.db.repository.UserRepository;
import guru.qa.rococo.db.repository.UserRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.GenerateUser;
import guru.qa.rococo.model.UserJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static guru.qa.rococo.util.FakerUtils.generateRandomName;

public class DbCreateUserExtension extends CreateUserExtension {

    private static final String DEFAULT_PASSWORD = "12345";
    private final UserRepository userRepository = new UserRepositoryHibernate();

    @Override
    protected UserJson createUserForTest(GenerateUser annotation) {
        AuthUserEntity authUser = new AuthUserEntity();
        authUser.setUsername(generateRandomName());
        authUser.setPassword(DEFAULT_PASSWORD);
        authUser.setEnabled(true);
        authUser.setAccountNonExpired(true);
        authUser.setAccountNonLocked(true);
        authUser.setCredentialsNonExpired(true);
        authUser.setAuthorities(new ArrayList<>(Arrays.stream(Authority.values())
                .map(a -> {
                    AuthorityEntity ae = new AuthorityEntity();
                    ae.setAuthority(a);
                    ae.setUser(authUser);
                    return ae;
                }).toList()));

        userRepository.createUserForTest(authUser);
        UserJson result = UserJson.fromEntity(authUser);
        result.setPassword(DEFAULT_PASSWORD);
        return result;
    }
}

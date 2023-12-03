package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should return user successfully from database")
    void findUserByDocumentCase1() {
        String document = "99999999901";
        UserDTO userDTO = new UserDTO("Name 1",
                "test",
                document,
                new BigDecimal(10),
                "name1@email.com",
                "4444",
                UserType.COMMON);

        createUser(userDTO);
        Optional<User> result = userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from database when user not exists")
    void findUserByDocumentCase2() {
        String document = "99999999901";
        UserDTO userDTO = new UserDTO("Name 1",
                "test",
                document,
                new BigDecimal(10),
                "name1@email.com",
                "4444",
                UserType.COMMON);

        Optional<User> result = userRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDTO data) {
        User newUser = new User(data);
        entityManager.persist(newUser);
        return newUser;
    }
}
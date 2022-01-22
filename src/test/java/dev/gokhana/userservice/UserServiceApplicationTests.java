package dev.gokhana.userservice;

import dev.gokhana.userservice.model.User;
import dev.gokhana.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"job.autorun.enabled=false"})
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class UserServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findAllTest() {
        List<User> users = Arrays.asList(new User("Gokhan", 15),
                new User("Corç", 25));

        Flux<User> flux = Flux.fromIterable(users);
        given(userRepository.findAll())
                .willReturn(flux);

        webTestClient.get().uri("api/v1/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .isEqualTo(users);
    }
}

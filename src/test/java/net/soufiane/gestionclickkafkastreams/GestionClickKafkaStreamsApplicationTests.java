package net.soufiane.gestionclickkafkastreams;

import net.soufiane.gestionclickkafkastreams.web.ClickController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GestionClickKafkaStreamsApplicationTests {

    @Autowired
    private ClickController clickController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Vérifie que le contrôleur est bien injecté
        assertThat(clickController).isNotNull();
    }

    @Test
    void indexPageLoads() {
        // Vérifie que la page d'accueil retourne un code 200
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("userId");
    }

}

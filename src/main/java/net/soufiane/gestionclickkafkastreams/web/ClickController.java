package net.soufiane.gestionclickkafkastreams.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

// Indique que cette classe est un contrôleur Spring MVC
@Controller
public class ClickController {

    // Injection du template Kafka pour envoyer des messages
    private final KafkaTemplate<String, String> kafkaTemplate;

    // Constructeur pour injecter le KafkaTemplate
    public ClickController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Affiche la page d'accueil et gère l'identifiant utilisateur en session
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // Récupère l'userId depuis la session, ou le crée s'il n'existe pas
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            session.setAttribute("userId", userId);
        }
        // Ajoute l'userId au modèle pour l'afficher dans la vue
        model.addAttribute("userId", userId);
        return "index"; // Retourne la vue "index"
    }

    // Gère le clic utilisateur (POST) et envoie un message Kafka
    @PostMapping("/click")
    public String click(@RequestParam String userId) {
        // Affiche dans la console l'userId qui a cliqué (pour debug)
        System.out.println("Clic reçu pour userId: " + userId);
        // Envoie un message sur le topic "clicks" avec userId comme clé et "click" comme valeur
        kafkaTemplate.send("clicks", userId, "click");
        // Redirige vers la page d'accueil
        return "redirect:/";
    }
}
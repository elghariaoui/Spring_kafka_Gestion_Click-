package net.soufiane.gestionclickkafkastreams.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


// Indique que cette classe est un service Spring
@Service
public class ClickCountConsumer {

    // Variable pour stocker le total des clics
    private long totalClicks = 0;

    // Méthode appelée à chaque message reçu sur le topic "click-counts"
    @KafkaListener(topics = "click-counts")
    public void listen(String key, Long count) {
        // Met à jour le total des clics avec la dernière valeur reçue
        totalClicks = count;
    }

    // Permet de récupérer le total des clics actuel
    public long getTotalClicks() {
        return totalClicks;
    }

}
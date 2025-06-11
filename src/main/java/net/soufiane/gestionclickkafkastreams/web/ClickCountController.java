package net.soufiane.gestionclickkafkastreams.web;

import jakarta.servlet.http.HttpSession;
import net.soufiane.gestionclickkafkastreams.service.ClickCountConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController // Indique que ce contrôleur retourne des données (JSON) par défaut
@RequestMapping("/clicks") // Préfixe toutes les routes par /clicks
public class ClickCountController {

    // Injection du service qui permet de récupérer le nombre de clics
    private final ClickCountConsumer consumer;

    public ClickCountController(ClickCountConsumer consumer) {
        this.consumer = consumer;
    }

    //@GetMapping("/count")
    //public long getCount() {
     //   return consumer.getTotalClicks();
    //}

    // Route GET /clicks/count qui retourne le nombre de clics au format JSON
    @GetMapping("/count")
    @ResponseBody
    public Map<String, Object> clickCount(HttpSession session) {
        // Récupère le userId stocké en session
        //String userId = (String) session.getAttribute("userId");
        // Récupère le nombre total de clics (à adapter si besoin pour le userId)
        Long count = consumer.getTotalClicks();;
        Map<String, Object> result = new HashMap<>();
        //result.put("userId", userId);
        //result.put("count", count);
        result.put("totalClicks", count);
        return result;
    }
}
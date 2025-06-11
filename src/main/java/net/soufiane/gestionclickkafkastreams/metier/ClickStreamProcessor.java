package net.soufiane.gestionclickkafkastreams.metier;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

// Active le support Kafka Streams dans Spring Boot
@EnableKafkaStreams
@Component
public class ClickStreamProcessor {

    // Déclare un bean Spring pour la topologie Kafka Streams
    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        System.out.println("Topologie Kafka Streams démarrée");

        // Crée un flux Kafka Streams à partir du topic "clicks" (clé et valeur en String)
        KStream<String, String> stream = builder.stream("clicks", Consumed.with(Serdes.String(), Serdes.String()));

        // Affiche chaque message reçu sur le topic "clicks" (pour debug)
        stream.peek((key, value) -> System.out.println("Message reçu sur clicks: " + key + " -> " + value));

        // Regroupe tous les événements sous la même clé "total" pour compter le nombre total de clics
        KTable<String, Long> totalcounts = stream
                .groupBy((key, value) -> "total", Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.as("click-counts-store"));

        // Affiche le résultat du comptage et écrit le total dans le topic "click-counts"
        totalcounts.toStream().peek((key, value) -> System.out.println("Comptage produit: " + key + " -> " + value))
                .to("click-counts", Produced.with(Serdes.String(), Serdes.Long()));

        // Retourne le flux d'origine (optionnel selon l'usage)
        return stream;
    }
}
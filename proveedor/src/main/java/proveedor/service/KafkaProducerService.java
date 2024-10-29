package proveedor.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Object message) {
        System.out.println("Enviando mensaje al tópico '" + topic + "'...");

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);

            future.thenAccept(result -> {
                System.out.println("Mensaje enviado con éxito al tópico '" + topic + "': " + message);
            }).exceptionally(ex -> {
                System.err.println("Error al enviar el mensaje al tópico '" + topic + "': " + ex.getMessage());
                return null;
            });
        } catch (Exception e) {
            System.err.println("Excepción al intentar enviar el mensaje al tópico '" + topic + "': " + e.getMessage());
        }
    }
}

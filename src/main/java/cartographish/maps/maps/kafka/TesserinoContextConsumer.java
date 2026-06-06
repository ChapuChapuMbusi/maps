package cartographish.maps.maps.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import cartographish.maps.maps.models.ContestoPescatore;
import cartographish.maps.maps.service.interfaces.ITesserinoContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TesserinoContextConsumer {

    private static final Logger log = LoggerFactory.getLogger(TesserinoContextConsumer.class);
    
    private final ITesserinoContextService tesserinoContextService;
    private final ObjectMapper objectMapper;

    public TesserinoContextConsumer(ITesserinoContextService tesserinoContextService, ObjectMapper objectMapper) {
        this.tesserinoContextService = tesserinoContextService;
        this.objectMapper = objectMapper;
    }

    /**
     * Ascolta i messaggi sul topic "tesserino-session-events".
     * Quando il modulo security pubblica il contesto, questo metodo lo intercetta.
     */
    @KafkaListener(topics = "tesserino-session-events", groupId = "${spring.kafka.consumer.group-id}")
    public void ascoltaContestoTesserino(String messaggioJson) {
        log.info("Ricevuto nuovo evento di sessione da Kafka: {}", messaggioJson);
        
        try {
            // 1. Convertiamo la stringa JSON ricevuta nel nostro POJO ContestoPescatore
            ContestoPescatore contesto = objectMapper.readValue(messaggioJson, ContestoPescatore.class);
            
            // 2. Invochiamo il servizio reattivo. 
            // NOTA: Essendo un flusso Mono (reattivo), in un consumer void tradizionale 
            // dobbiamo fare il .subscribe() per attivare la pipeline e scatenare l'elaborazione.
            tesserinoContextService.elaboraContestoMappa(contesto)
                .subscribe(
                    risposta -> log.info("Elaborazione contesto e arricchimento ISPRA completati con successo per il fiume: {}", 
                            risposta.getGeolocalizzazionePesca().getNomeCorpoIdrico()),
                    errore -> log.error("Errore durante l'elaborazione del contesto mappa: ", errore)
                );
                
        } catch (Exception e) {
            log.error("Errore di deserializzazione del messaggio Kafka: ", e);
        }
    }
}
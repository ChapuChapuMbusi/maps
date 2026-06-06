package cartographish.maps.maps.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cartographish.maps.maps.models.ContestoPescatore;
import cartographish.maps.maps.response.ArricchimentoTesserinoResponse;
import cartographish.maps.maps.service.interfaces.ITesserinoContextService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/maps/tesserino")
public class TesserinoController {

    // Modificato da TesserinoContextServiceImpl a ITesserinoContextService
    private final ITesserinoContextService tesserinoContextService;

    // Anche il costruttore ora accetta l'interfaccia coerentemente con gli altri controller
    public TesserinoController(ITesserinoContextService tesserinoContextService) {
        this.tesserinoContextService = tesserinoContextService;
    }

    /**
     * Endpoint POST per attivare la sessione del tesserino ed elaborare la mappa.
     * URL: http://localhost:10002/api/maps/tesserino/attiva-sessione
     */
    @PostMapping("/attiva-sessione")
    public Mono<ResponseEntity<ArricchimentoTesserinoResponse>> attivaSessioneTesserino(
            @RequestBody ContestoPescatore contesto) {
        
        return tesserinoContextService.elaboraContestoMappa(contesto)
                .map(risposta -> ResponseEntity.ok(risposta))
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Ritorna 404 se il flusso è vuoto
    }
}

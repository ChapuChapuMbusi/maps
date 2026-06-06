package cartographish.maps.maps.controller;

import cartographish.maps.maps.service.implementations.ExternalApiServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    private final ExternalApiServiceImpl externalApiService;

    // Iniettiamo il Service nel Controller tramite costruttore
    public ExternalApiController(ExternalApiServiceImpl externalApiService) {
        this.externalApiService = externalApiService;
    }

    // Esponiamo l'endpoint GET: http://localhost:10002/api/external/corpi-idrici
    @GetMapping("/corpi-idrici")
    public Mono<String> getCorpiIdrici(@RequestParam(required = false) String name) {
        return externalApiService.getCorpiIdriciReali(name);
    }
}
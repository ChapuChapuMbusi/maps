package cartographish.maps.maps.service.implementations;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiServiceImpl {

    private final WebClient webClient;

    // Il costruttore ha lo stesso nome della classe e riceve il Builder di Spring
    public ExternalApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://dati.isprambiente.it").build();
    }

    public Mono<String> getCorpiIdriciReali() {
    // Query di test: prendi 10 triple a caso
    String sparqlQuery = "SELECT * WHERE { ?s ?p ?o } LIMIT 10";

    return this.webClient.post()
            .uri("/sparql") // Usiamo il path relativo visto che hai impostato il baseUrl
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Accept", "application/sparql-results+json")
            .bodyValue("query=" + URLEncoder.encode(sparqlQuery, StandardCharsets.UTF_8) 
                       + "&format=application/sparql-results+json")
            .retrieve()
            .bodyToMono(String.class);
    }
}
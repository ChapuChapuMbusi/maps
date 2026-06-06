package cartographish.maps.maps.service.implementations;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiServiceImpl {

    private final WebClient webClient;

    public ExternalApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://dati.isprambiente.it").build();
    }

    public Mono<String> getCorpiIdriciReali(String name) {
    String termineRicerca = (name == null || name.trim().isEmpty()) ? "Tevere" : name;

    // Sostituiamo OPTIONAL con l'obbligatorietà di geo:lat e geo:long
    // In questo modo il JSON conterrà ESCLUSIVAMENTE punti pronti per la mappa
    String sparqlQuery = 
        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
        "SELECT DISTINCT ?entita ?nome ?tipo ?lat ?long WHERE { " +
        "  ?entita rdfs:label ?nome . " +
        "  ?entita rdf:type ?tipo . " +
        "  ?entita geo:lat ?lat . " +
        "  ?entita geo:long ?long . " +
        "  FILTER(regex(str(?nome), \"" + termineRicerca + "\", \"i\")) " +
        "  FILTER(?tipo != <http://rs.tdwg.org/dwc/terms/Occurrence> && " +
        "         ?tipo != <http://rs.tdwg.org/dwc/terms/Event> && " +
        "         ?tipo != <http://semanticweb.cs.vu.nl/2009/11/sem/Event> && " +
        "         ?tipo != <https://w3id.org/italia/env/onto/inspire-mf/Observation> && " +
        "         ?tipo != <http://purl.org/dsw/Token>) " +
        "} LIMIT 50";

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("query", sparqlQuery);
    formData.add("format", "application/sparql-results+json");

    return this.webClient.post()
            .uri("/sparql")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Accept", "application/json, application/sparql-results+json")
            .bodyValue(formData)
            .retrieve()
            .bodyToMono(String.class);
    }
}
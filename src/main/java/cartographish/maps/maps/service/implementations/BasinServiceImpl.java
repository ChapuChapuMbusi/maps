package cartographish.maps.maps.service.implementations;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import cartographish.maps.maps.dto.BasinDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.Basin;
import cartographish.maps.maps.repository.BasinRepository;
import cartographish.maps.maps.request.BasinRequest;
import cartographish.maps.maps.service.interfaces.IBasinService;
import reactor.core.publisher.Mono;

@Service
public class BasinServiceImpl implements IBasinService{

    @Autowired
    private WebClient webClient;

    @Autowired
    private BasinRepository basinR;
    @Override
    public List<BasinDTO> getAllZones() {
        return basinR.findAll().stream().map(basin -> new BasinDTO(basin.getBasinCode(), basin.getBasinName())).collect(Collectors.toList());
    }

    @Override
    public Basin getBasinById(String id) throws CustomException {
       Optional<Basin> bOptional = basinR.findById(id);
       if (bOptional.isEmpty()) {
           throw new CustomException("Basin not found with ID: " + id);
       }

       return bOptional.get();
    }

    @Override
    @Transactional
    public void createUpdateBasin(BasinRequest req) throws CustomException {
        Basin basin;
        if(req.getId() != null){
            Optional<Basin> bOptional = basinR.findById(req.getId());
             if(bOptional.isEmpty()){
                throw new CustomException("Unable to update: persistent basin");
            }
            basin = bOptional.get();
        } else {
            basin = new Basin();
        }

        basin.setBasinCode(req.getBasinCode());
        basin.setBasinName(req.getBasinName());

        basinR.save(basin);
    }

    @Override
    public void deleteBasin(String id) throws CustomException {
        Optional<Basin> bOptional = basinR.findById(id);
        if(bOptional.isEmpty()){
            throw new CustomException("Basin not found with ID: " + id);
        }
        basinR.deleteById(bOptional.get().getId());
    }

    @Override
@Transactional
public List<Basin> fetchAndSaveExternalBasins() throws CustomException {
    String sparqlQuery = """
        PREFIX ispra-top: <https://w3id.org/italia/env/onto/top/>
        PREFIX ispra-plc: <https://w3id.org/italia/env/onto/place/>
        PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
        PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

        SELECT DISTINCT 
          (str(?year) AS ?year)
          ?bdesc
          ?lat
          ?long
          ?mundesc
          ?provdesc
          ?regdesc
        WHERE {
          ?ind ispra-top:isMemberOf ?ic ;
               ispra-top:atTime ?time .
          ?time ispra-top:year ?year .

          GRAPH <https://w3id.org/italia/env/ld/bathw/> {
            ?ic ispra-top:isPartOf ?icmun .
            ?icmun ispra-top:isPartOf ?collmun .
            ?collmun ispra-top:isCollectionOf ?mun .
            ?ic ispra-top:isPartOf ?collbath .
            ?collbath ispra-top:isCollectionOf ?bath .

            ?bath ispra-top:name ?bdesc ;
                  geo:lat ?lat ;
                  geo:long ?long .

            ?mun rdfs:label ?mundesc ;
                 ispra-plc:hasDirectHigherRank ?prov ;
                 ispra-plc:hasRegion ?reg .
            ?prov rdfs:label ?provdesc .
            ?reg rdfs:label ?regdesc .
          }
        }
        ORDER BY ?year
    """;

    try {
        // Corpo della richiesta SPARQL
        String body = "query=" + URLEncoder.encode(sparqlQuery, StandardCharsets.UTF_8) + "&format=json";

        // Chiamata al servizio SPARQL
        Map<String, Object> response = webClient.post()
                .uri("https://dati.isprambiente.it/sparql")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Debug: stampo l'intera risposta SPARQL
        System.out.println("SPARQL response: " + response);

        if (response == null || !response.containsKey("results")) {
            System.out.println("Nessun risultato trovato nella risposta SPARQL.");
            return List.of();
        }

        List<Map<String, Object>> bindings =
                (List<Map<String, Object>>)((Map)response.get("results")).get("bindings");

        if (bindings.isEmpty()) {
            System.out.println("Bindings vuoti.");
            return List.of();
        }

        List<Basin> basins = new ArrayList<>();
        for (Map<String, Object> binding : bindings) {
            String year = getValue(binding, "year");
            String bdesc = getValue(binding, "bdesc");
            String lat = getValue(binding, "lat");
            String lon = getValue(binding, "long");

            // Debug per ogni basin
            System.out.println("Basin trovato -> year: " + year + ", name: " + bdesc + ", lat: " + lat + ", long: " + lon);

            Basin basin = new Basin();
            basin.setBasinCode(year);
            basin.setBasinName(bdesc);
            basins.add(basin);
        }

        // Salvataggio dei dati e debug
        List<Basin> savedBasins = basinR.saveAll(basins);
        System.out.println("Basins salvati: " + savedBasins.size());
        return savedBasins;

    } catch (Exception e) {
        e.printStackTrace();
        throw new CustomException("Errore durante fetch SPARQL: " + e.getMessage());
    }
}



    // funzione di utilità per leggere i valori da SPARQL JSON
    private String getValue(Map<String, Object> binding, String key) {
        if (binding.containsKey(key)) {
            Map<String, Object> val = (Map<String, Object>) binding.get(key);
            return (String) val.get("value");
        }
        return null;
    }

}

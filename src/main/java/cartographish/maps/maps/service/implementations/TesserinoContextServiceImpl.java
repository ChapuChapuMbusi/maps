package cartographish.maps.maps.service.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cartographish.maps.maps.models.ContestoPescatore;
import cartographish.maps.maps.models.GeoJsonModels.FeatureCollection;
import cartographish.maps.maps.models.GeoJsonModels.GeoJsonFeature;
import cartographish.maps.maps.models.GeoJsonModels.Geometry;
import cartographish.maps.maps.models.RegimePesca; // Allineato su .models
import cartographish.maps.maps.response.ArricchimentoTesserinoResponse;
import cartographish.maps.maps.service.interfaces.ITesserinoContextService;
import cartographish.maps.maps.repository.TesserinoContextRepository;

import org.springframework.stereotype.Service;
import java.util.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers; // Importato per gestire l'I/O bloccante

@Service
public class TesserinoContextServiceImpl implements ITesserinoContextService {

    private final ExternalApiServiceImpl externalApiService;
    private final ObjectMapper objectMapper;
    private final TesserinoContextRepository tesserinoContextRepository;

    public TesserinoContextServiceImpl(ExternalApiServiceImpl externalApiService, 
                                       ObjectMapper objectMapper, 
                                       TesserinoContextRepository tesserinoContextRepository) {
        this.externalApiService = externalApiService;
        this.objectMapper = objectMapper;
        this.tesserinoContextRepository = tesserinoContextRepository;
    }

    @Override
    public Mono<ArricchimentoTesserinoResponse> elaboraContestoMappa(ContestoPescatore contesto) {
        String nomeFiume = (contesto.getGeolocalizzazionePesca() != null) 
                ? contesto.getGeolocalizzazionePesca().getNomeCorpoIdrico() 
                : "Tevere";

        // Chiamata asincrona a ISPRA
        return externalApiService.getCorpiIdriciReali(nomeFiume)
                .flatMap(ispraRawJson -> Mono.fromCallable(() -> {
                    
                    // 1. Salva il contesto su MongoDB (Operazione bloccante)
                    tesserinoContextRepository.save(contesto);
                    
                    List<GeoJsonFeature> featuresFiltrate = new ArrayList<>();
                    JsonNode root = objectMapper.readTree(ispraRawJson);
                    JsonNode bindings = root.path("results").path("bindings");

                    String nomeFiumeContesto = nomeFiume.toLowerCase();
                    
                    double latPescatore = contesto.getGeolocalizzazionePesca() != null ? contesto.getGeolocalizzazionePesca().getLatitudine() : 41.9028;
                    double lonPescatore = contesto.getGeolocalizzazionePesca() != null ? contesto.getGeolocalizzazionePesca().getLongitudine() : 12.4964;

                    for (JsonNode binding : bindings) {
                        double latIspra = binding.path("lat").path("value").asDouble();
                        double lonIspra = binding.path("long").path("value").asDouble();
                        String nomeIspra = binding.path("nome").path("value").asText().toLowerCase();

                        boolean isStessoCorpoIdrico = nomeIspra.contains(nomeFiumeContesto);
                        boolean isVicino = calcolaDistanzaInKm(latPescatore, lonPescatore, latIspra, lonIspra) < 20.0;

                        if (isStessoCorpoIdrico || isVicino) {
                            Geometry geometry = new Geometry(lonIspra, latIspra);

                            Map<String, Object> properties = new LinkedHashMap<>();
                            properties.put("entita_uri", binding.path("entita").path("value").asText());
                            properties.put("tipo_oggetto", binding.path("tipo").path("value").asText());
                            properties.put("denominazione", binding.path("nome").path("value").asText());

                            GeoJsonFeature feature = new GeoJsonFeature(geometry, properties);
                            featuresFiltrate.add(feature);
                        }
                    }

                    FeatureCollection livelliCartografici = new FeatureCollection(featuresFiltrate);

                    RegimePesca regime = new RegimePesca();
                    String codiceZona = contesto.getGeolocalizzazionePesca() != null ? contesto.getGeolocalizzazionePesca().getCodiceZona() : "B";
                    regime.setClassificazioneAcque("ACQUE SECONDARIE " + codiceZona);
                    regime.setObbligoCambioScheda(false);

                    Map<String, Object> modalita = new LinkedHashMap<>();
                    modalita.put("noKillDisponibile", true);
                    modalita.put("pescaGenericaConsentita", true);
                    regime.setModalitaConsentite(modalita);

                    ArricchimentoTesserinoResponse risposta = new ArricchimentoTesserinoResponse();
                    risposta.setInformazioniTemporali(contesto.getInformazioniTemporali());
                    risposta.setGeolocalizzazionePesca(contesto.getGeolocalizzazionePesca());
                    risposta.setRegimePesca(regime);
                    risposta.setLivelliCartograficiIspra(livelliCartografici);

                    return risposta;
                })
                // Sposta l'esecuzione di questa callable sul pool elastico per non bloccare Netty
                .subscribeOn(Schedulers.boundedElastic())); 
    }

    private double calcolaDistanzaInKm(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(radLat1) * Math.cos(radLat2) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        return 6371.0 * (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))); 
    }
}
package cartographish.maps.maps.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cartographish.maps.maps.mapper.WaterBodyMapper;
import cartographish.maps.maps.models.WaterBody;
import cartographish.maps.maps.repository.WaterBodyRepository;
import cartographish.maps.maps.response.ExternalWaterBodyResponse;
import cartographish.maps.maps.service.interfaces.IExternalApiService;

@Service
public class ExternalApiServiceImpl implements IExternalApiService{
    
    private final WebClient webClient;
    private final WaterBodyRepository wBodyRepository;

    public ExternalApiServiceImpl(WebClient.Builder builder, WaterBodyRepository respo){

        this.webClient = builder.baseUrl("https://dati.isprambiente.it").build();
        this.wBodyRepository = respo;
    }

    @Override
    public List<WaterBody> fetchAndSavWaterBodies(double lat, double lon) {
       
        List<ExternalWaterBodyResponse> externalList = webClient.get()
        .uri("/bacini?lat={lat}&lon={lon}", lat, lon)
        .retrieve()
        .bodyToFlux(ExternalWaterBodyResponse.class)
        .collectList()
        .block();

        List<WaterBody> waterBodies = externalList.stream()
        .map(WaterBodyMapper::fromExternal)
        .collect(Collectors.toList());

        return wBodyRepository.saveAll(waterBodies);
    }

}

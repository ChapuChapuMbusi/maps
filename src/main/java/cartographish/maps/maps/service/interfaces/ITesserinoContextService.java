package cartographish.maps.maps.service.interfaces;

import cartographish.maps.maps.models.ContestoPescatore;
import cartographish.maps.maps.response.ArricchimentoTesserinoResponse;
import reactor.core.publisher.Mono;

public interface ITesserinoContextService {
    Mono<ArricchimentoTesserinoResponse> elaboraContestoMappa(ContestoPescatore contesto);
}

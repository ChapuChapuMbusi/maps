package cartographish.maps.maps.response;

import cartographish.maps.maps.models.GeoJsonModels.FeatureCollection;
import cartographish.maps.maps.models.GeolocalizzazionePesca;
import cartographish.maps.maps.models.InformazioniTemporali;
import cartographish.maps.maps.models.RegimePesca; // Allineato su .models
import lombok.Data;

@Data
public class ArricchimentoTesserinoResponse {
    private InformazioniTemporali informazioniTemporali;
    private GeolocalizzazionePesca geolocalizzazionePesca;
    private RegimePesca regimePesca;
    private FeatureCollection livelliCartograficiIspra;
}
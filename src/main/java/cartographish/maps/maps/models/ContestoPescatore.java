package cartographish.maps.maps.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "contesto_pescatore")
public class ContestoPescatore {
    @Id
    private String id;

    private InformazioniTemporali informazioniTemporali;
    private GeolocalizzazionePesca geolocalizzazionePesca;
}

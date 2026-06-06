package cartographish.maps.maps.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "geolocalizzazione_pesca")
public class GeolocalizzazionePesca {

    private String provinciaCompetenza;
    private int codiceBacino;
    private String nomeCorpoIdrico;
    private String codiceZona;
    private String limitiTrattoDescrizione;
    private double latitudine;
    private double longitudine;
}

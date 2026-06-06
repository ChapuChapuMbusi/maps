package cartographish.maps.maps.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "informazioni_temporali")
public class InformazioniTemporali {

    private int anno;
    private String mese;
    private int giorno;
    private String sessioneGiornaliera;
}

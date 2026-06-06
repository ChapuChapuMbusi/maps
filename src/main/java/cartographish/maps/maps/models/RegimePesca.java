package cartographish.maps.maps.models;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "regime_pesca")
public class RegimePesca {
private String classificazioneAcque; // es. "ACQUE SECONDARIE B"
    private boolean obbligoCambioScheda;
    private Map<String, Object> modalitaConsentite;
}

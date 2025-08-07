package cartographish.maps.maps.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "zone")
public class Zone {
    
    @Id
    private Integer id;
    private String zoneCode; //Codice dellla zona di pesca, esemp.(A, B, C, D)
}

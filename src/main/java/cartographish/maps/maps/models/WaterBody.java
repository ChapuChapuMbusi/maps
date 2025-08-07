package cartographish.maps.maps.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Document(collection = "water_bodies")
public class WaterBody {

    @Id
    private Integer id;
    private String name; //Nome del corpo d'acqua esemp.(Fiume, Torrente, Lago, ecc.)
    private Basin basin;
    private Zone zone;
    private String waterCategory; //Categoria acqua (principale, secondaria A, secondaria B)
    private GeoLocation geoLocation;
}

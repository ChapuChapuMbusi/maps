package cartographish.maps.maps.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Document(collection = "water_bodies")
public class WaterBody {

    @Id
    private String id;
    private String name;
    private Basin basin;
    private Zone zone;
    private String waterQuality;
}

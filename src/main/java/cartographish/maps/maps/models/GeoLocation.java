package cartographish.maps.maps.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "geo_locations")
public class GeoLocation {

    @Id
    private Integer id;
    private double latitudine;
    private double longitudine;
}

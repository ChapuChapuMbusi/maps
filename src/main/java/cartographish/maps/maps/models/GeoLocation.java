package cartographish.maps.maps.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "geo_location")
public class GeoLocation {

    private double latitudine;
    private double longitudine;
}

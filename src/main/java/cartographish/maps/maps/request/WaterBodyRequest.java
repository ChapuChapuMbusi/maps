package cartographish.maps.maps.request;

import lombok.Data;

@Data
public class WaterBodyRequest {

    private Integer id;
    private String name;
    private BasinRequest basin;
    private ZoneRequest zone;
    private String waterCategory;
    private GeoLocationRequest geoLocation;
}

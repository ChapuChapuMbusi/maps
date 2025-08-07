package cartographish.maps.maps.request;

import lombok.Data;

@Data
public class GeoLocationRequest {

    private Integer id;
    private Double latitude;
    private Double longitude;
}

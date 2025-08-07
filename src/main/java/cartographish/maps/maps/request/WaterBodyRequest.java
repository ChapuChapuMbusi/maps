package cartographish.maps.maps.request;

import lombok.Data;

@Data
public class WaterBodyRequest {

    @NotBlank
    private String name;

    @NotNull
    private BasinRequest basin;

    @NotNull
    private ZoneRequest zone;

    private String waterCategory;

    @NotNull
    private GeoLocationRequest geoLocation;
}

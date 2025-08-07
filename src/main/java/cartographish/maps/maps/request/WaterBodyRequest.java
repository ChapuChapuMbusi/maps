package cartographish.maps.maps.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

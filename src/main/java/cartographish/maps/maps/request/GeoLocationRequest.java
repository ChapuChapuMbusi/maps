package cartographish.maps.maps.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeoLocationRequest {
    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}

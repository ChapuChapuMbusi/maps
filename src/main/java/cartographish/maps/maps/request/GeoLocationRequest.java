package cartographish.maps.maps.request;

import lombok.Data;

@Data
public class GeoLocationRequest {
    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}

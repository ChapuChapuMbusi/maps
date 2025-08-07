package cartographish.maps.maps.request;

import lombok.Data;

@Data
public class ZoneRequest {

    @NotBlank
    private String zoneCode;
}

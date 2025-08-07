package cartographish.maps.maps.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ZoneRequest {

    @NotBlank
    private String zoneCode;
}

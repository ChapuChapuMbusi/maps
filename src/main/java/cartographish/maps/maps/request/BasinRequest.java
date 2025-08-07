package cartographish.maps.maps.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BasinRequest {

    @NotBlank
    private String basinCode;

    @NotBlank
    private String basinName;
}

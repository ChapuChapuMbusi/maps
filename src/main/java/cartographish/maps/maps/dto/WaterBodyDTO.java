package cartographish.maps.maps.dto;

import cartographish.maps.maps.models.Basin;
import cartographish.maps.maps.models.Zone;
import lombok.Data;

@Data
public class WaterBodyDTO {

    private String id;
    private String name;
    private Basin basin;
    private Zone zone;
    private String waterQuality;

}

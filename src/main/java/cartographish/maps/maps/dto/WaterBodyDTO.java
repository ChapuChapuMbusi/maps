package cartographish.maps.maps.dto;
import lombok.Data;

@Data
public class WaterBodyDTO {

    private String id;
    private String name;
    private Basin basin;
    private Zone zone;
    private String waterQuality;

}

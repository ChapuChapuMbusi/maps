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


    public WaterBodyDTO(String id, String name, Basin basin, Zone zone, String waterQuality){

        this.id = id;
        this.name = name;
        this.basin = basin;
        this.zone = zone;
        this.waterQuality = waterQuality;
    }




}


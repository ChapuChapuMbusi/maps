package cartographish.maps.maps.dto;
import lombok.Data;

@Data
public class WaterBodyDTO {

    private String id;
    private String name;
    private BasinDTO basinDTO;
    private ZoneDTO zoneDTO;
    private String waterCategory;
    private GeoLocationDTO geoLocationDTO;


    public WaterBodyDTO(String id, String name, BasinDTO basinDTO, ZoneDTO zoneDTO, String waterCategory, GeoLocationDTO geoLocationDTO){

        this.id = id;
        this.name = name;
        this.basinDTO = basinDTO;
        this.zoneDTO = zoneDTO;
        this.waterCategory = waterCategory;
        this.geoLocationDTO = geoLocationDTO;
    }
}


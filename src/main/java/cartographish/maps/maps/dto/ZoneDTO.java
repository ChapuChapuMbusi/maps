package cartographish.maps.maps.dto;

import lombok.Data;

@Data
public class ZoneDTO {

    private Integer id;
    private String zoneCode;

    public ZoneDTO(String zoneCode) {
        this.zoneCode = zoneCode;
    }
}

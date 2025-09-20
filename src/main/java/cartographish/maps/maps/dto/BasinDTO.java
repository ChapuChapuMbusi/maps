package cartographish.maps.maps.dto;

import lombok.Data;

@Data
public class BasinDTO {

    
    private String id;
    private String basinCode;
    private String basinName;

    public BasinDTO(String basinCode, String basinName) {
        this.basinCode = basinCode;
        this.basinName = basinName;
    }
}

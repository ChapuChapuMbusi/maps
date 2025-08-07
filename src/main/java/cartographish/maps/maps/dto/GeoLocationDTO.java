package cartographish.maps.maps.dto;

import lombok.Data;

@Data
public class GeoLocationDTO {

    private Integer id;
    private double latitudine;
    private double longitudine;

    public GeoLocationDTO(double latitudine, double longitudine) {
        this.latitudine = latitudine;       
        this.longitudine = longitudine;
    }
}

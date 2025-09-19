package cartographish.maps.maps.response;

import lombok.Data;

@Data
public class ExternalWaterBodyResponse {
    private String codiceBacino;   // es: IT-001
    private String nomeAcqua;      // es: Fiume Po
    private String zonaPesca;      // es: A
    private String categoriaAcqua; // es: Principale
    private double lat;
    private double lon;
}


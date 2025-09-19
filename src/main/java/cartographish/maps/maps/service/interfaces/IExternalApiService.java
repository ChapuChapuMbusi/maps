package cartographish.maps.maps.service.interfaces;

import java.util.List;

import cartographish.maps.maps.models.WaterBody;

public interface IExternalApiService {

    List<WaterBody> fetchAndSavWaterBodies(double lat, double lon);
}

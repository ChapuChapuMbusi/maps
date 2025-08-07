package cartographish.maps.maps.service;
import cartographish.maps.maps.dto.WaterBodyDTO;
import java.util.List;

public interface IWaterBodyService {

    List<WaterBodyDTO> getAllWaterBodies();
    WaterBodyDTO getWaterBodyById(String id);
}

package cartographish.maps.maps.service.interfaces;
import cartographish.maps.maps.dto.WaterBodyDTO;
import java.util.List;

public interface IWaterBodyService {

    List<WaterBodyDTO> getAllWaterBodies();
    WaterBodyDTO getWaterBodyById(String id);
}

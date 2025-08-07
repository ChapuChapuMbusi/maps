package cartographish.maps.maps.service.interfaces;
import cartographish.maps.maps.dto.WaterBodyDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.WaterBody;

import java.util.List;

public interface IWaterBodyService {

    List<WaterBodyDTO> getAllWaterBodies();
    WaterBody getWaterBodyById(String id) throws CustomException;
    Boolean checkName(String name) throws CustomException;
    
}

package cartographish.maps.maps.service.interfaces;
import cartographish.maps.maps.dto.WaterBodyDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.WaterBody;
import cartographish.maps.maps.request.WaterBodyRequest;

import java.util.List;

public interface IWaterBodyService {

    List<WaterBodyDTO> getAllWaterBodies();
    WaterBody getWaterBodyById(Integer id) throws CustomException;
    Boolean checkName(String name) throws CustomException;
    void createUpdateWaterBody(WaterBodyRequest req) throws CustomException;
    void deleteWaterBody(Integer id) throws CustomException;
}

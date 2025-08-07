package cartographish.maps.maps.service.interfaces;

import java.util.List;

import cartographish.maps.maps.dto.BasinDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.Basin;
import cartographish.maps.maps.request.BasinRequest;


public interface IBasinService {
    List<BasinDTO> getAllZones();
    Basin getBasinById(Integer id) throws CustomException;
    void createUpdateBasin(BasinRequest req) throws CustomException;
    void deleteBasin(Integer id) throws CustomException;
}

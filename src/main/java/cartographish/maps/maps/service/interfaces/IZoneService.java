package cartographish.maps.maps.service.interfaces;

import java.util.List;

import cartographish.maps.maps.dto.ZoneDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.Zone;
import cartographish.maps.maps.request.ZoneRequest;

public interface IZoneService {

    List<ZoneDTO> getAllZones();
    Zone getZoneById(Integer id) throws CustomException;
    void createUpdateZone(ZoneRequest req) throws CustomException;
    void deleteZone(Integer id) throws CustomException;
}

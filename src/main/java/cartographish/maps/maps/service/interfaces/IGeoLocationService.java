package cartographish.maps.maps.service.interfaces;

import java.util.List;

import cartographish.maps.maps.dto.GeoLocationDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.GeoLocation;
import cartographish.maps.maps.request.GeoLocationRequest;

public interface IGeoLocationService {
    List<GeoLocationDTO> getAllGeoLocations();
    GeoLocation getGeoLocationById(Integer id) throws CustomException;
    void createUpdateGeoLocation(GeoLocationRequest request) throws CustomException;
    void deleteGeoLocation(Integer id) throws CustomException;
}

package cartographish.maps.maps.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cartographish.maps.maps.dto.GeoLocationDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.GeoLocation;
import cartographish.maps.maps.repository.GeoLocationRepository;
import cartographish.maps.maps.request.GeoLocationRequest;
import cartographish.maps.maps.service.interfaces.IGeoLocationService;

@Service
public class GeoLocationServiceImpl implements IGeoLocationService{

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @Override
    public List<GeoLocationDTO> getAllGeoLocations() {
        return geoLocationRepository.findAll()
                .stream()
                .map(geo -> new GeoLocationDTO(geo.getLatitudine(), geo.getLongitudine()))
                .collect(Collectors.toList());
    }

    @Override
    public GeoLocation getGeoLocationById(Integer id) throws CustomException {
        Optional<GeoLocation> optional = geoLocationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new CustomException("GeoLocation not found with ID: " + id);
        }
        return optional.get();
    }

    @Override
    @Transactional
    public void createUpdateGeoLocation(GeoLocationRequest request) throws CustomException {
        GeoLocation geoLocation;

        if (request.getId() != null) {
            Optional<GeoLocation> optional = geoLocationRepository.findById(request.getId());
            if (optional.isEmpty()) {
                throw new CustomException("Unable to update: GeoLocation not found");
            }
            geoLocation = optional.get();
        } else {
            geoLocation = new GeoLocation();
        }

        geoLocation.setLatitudine(request.getLatitude());
        geoLocation.setLongitudine(request.getLongitude());

        geoLocationRepository.save(geoLocation);
    }

    @Override
    public void deleteGeoLocation(Integer id) throws CustomException {
        if (!geoLocationRepository.existsById(id)) {
            throw new CustomException("GeoLocation not found with ID: " + id);
        }
        geoLocationRepository.deleteById(id);
    }
}

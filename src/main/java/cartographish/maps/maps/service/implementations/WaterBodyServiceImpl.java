package cartographish.maps.maps.service.implementations;
import cartographish.maps.maps.dto.BasinDTO;
import cartographish.maps.maps.dto.GeoLocationDTO;
import cartographish.maps.maps.dto.WaterBodyDTO;
import cartographish.maps.maps.dto.ZoneDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.Basin;
import cartographish.maps.maps.models.GeoLocation;
import cartographish.maps.maps.models.WaterBody;
import cartographish.maps.maps.models.Zone;
import cartographish.maps.maps.repository.WaterBodyRepository;
import cartographish.maps.maps.request.WaterBodyRequest;
import cartographish.maps.maps.service.interfaces.IWaterBodyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WaterBodyServiceImpl implements IWaterBodyService{

    @Autowired
    private WaterBodyRepository waterBodyR;
    @Override
    public List<WaterBodyDTO> getAllWaterBodies() {
        return waterBodyR.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public WaterBody getWaterBodyById(Integer id) throws CustomException {
        Optional<WaterBody> wOptional = waterBodyR.findById(id);

        if (wOptional.isEmpty()) {
            throw new CustomException("Water Body not found with ID: " + id);
        }
       
        return wOptional.get();
    }
    

    @Override
    public Boolean checkName(String name) throws CustomException {
         Optional<WaterBody> wOptional = waterBodyR.findByName(name);
         return wOptional.isPresent();
    }


    @Override
    @Transactional
    public void createUpdateWaterBody(WaterBodyRequest req) throws CustomException {
        WaterBody wBody;

        if(req.getId() != null){
            Optional<WaterBody> wOptional = waterBodyR.findById(req.getId());
            if(wOptional.isEmpty()){
                throw new CustomException("Unable to update: persistent body of water");
            }
            wBody = wOptional.get();
        } else {
            wBody = new WaterBody();
        }

        wBody.setName(req.getName());

        Basin basin = new Basin();
        if (req.getBasin() != null) {
            basin.setBasinCode(req.getBasin().getBasinCode());
            basin.setBasinName(req.getBasin().getBasinName());
        }
        wBody.setBasin(basin);

        Zone zone = new Zone();
        if (req.getZone() != null) {
            zone.setZoneCode(req.getZone().getZoneCode());
        }
        wBody.setZone(zone);

        wBody.setWaterCategory(req.getWaterCategory());

        GeoLocation geoLocation = new GeoLocation();
        if (req.getGeoLocation() != null) {
            geoLocation.setLatitudine(req.getGeoLocation().getLatitude());
            geoLocation.setLongitudine(req.getGeoLocation().getLongitude());
        }
        wBody.setGeoLocation(geoLocation);

        waterBodyR.save(wBody);
    }

    @Override
    @Transactional
    public void deleteWaterBody(Integer id) throws CustomException {
        Optional<WaterBody> wOptional = waterBodyR.findById(id);
        if(wOptional.isEmpty()){
            throw new CustomException("Water Body not found with ID: " + id);
        }
        waterBodyR.deleteById(wOptional.get().getId());
    }

    
    private WaterBodyDTO convertToDTO(WaterBody wB) {
        BasinDTO basinDTO = new BasinDTO(wB.getBasin().getBasinCode(), wB.getBasin().getBasinName());
        ZoneDTO zoneDTO = new ZoneDTO(wB.getZone().getZoneCode());
        GeoLocationDTO geoLocationDTO = new GeoLocationDTO(wB.getGeoLocation().getLatitudine(), wB.getGeoLocation().getLongitudine());

        return new WaterBodyDTO(wB.getId(),wB.getName(),basinDTO,zoneDTO,wB.getWaterCategory(),geoLocationDTO);
    }

}

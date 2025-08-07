package cartographish.maps.maps.service.implementations;
import cartographish.maps.maps.dto.BasinDTO;
import cartographish.maps.maps.dto.GeoLocationDTO;
import cartographish.maps.maps.dto.WaterBodyDTO;
import cartographish.maps.maps.dto.ZoneDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.exception.WaterBodyNotFoundException;
import cartographish.maps.maps.models.WaterBody;
import cartographish.maps.maps.repository.WaterBodyRepository;
import cartographish.maps.maps.request.WaterBodyRequest;
import cartographish.maps.maps.service.interfaces.IWaterBodyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
         Optional<WaterBody> wOptional = waterBodyR.getNameByWaterBody(name);
         return wOptional.isPresent();
    }


    @Override
    public void createWaterBody(WaterBodyRequest req) throws CustomException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWaterBody'");
    }

    @Override
    public void updateWaterBody(WaterBodyRequest req) throws CustomException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateWaterBody'");
    }

    @Override
    public void deleteWaterBody(WaterBodyRequest req) throws CustomException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteWaterBody'");
    }

    
    private WaterBodyDTO convertToDTO(WaterBody wB) {
        BasinDTO basinDTO = new BasinDTO(wB.getBasin().getBasinCode(), wB.getBasin().getBasinName());
        ZoneDTO zoneDTO = new ZoneDTO(wB.getZone().getZoneCode());
        GeoLocationDTO geoLocationDTO = new GeoLocationDTO(wB.getGeoLocation().getLatitudine(), wB.getGeoLocation().getLongitudine());

        return new WaterBodyDTO(wB.getId(),wB.getName(),basinDTO,zoneDTO,wB.getWaterCategory(),geoLocationDTO);
    }

}

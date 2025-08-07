package cartographish.maps.maps.service.implementations;
import cartographish.maps.maps.dto.WaterBodyDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.exception.WaterBodyNotFoundException;
import cartographish.maps.maps.models.WaterBody;
import cartographish.maps.maps.repository.WaterBodyRepository;
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
    public WaterBody getWaterBodyById(String id) throws CustomException {
        Optional<WaterBody> wOptional = waterBodyR.findById(id);

        if (wOptional.isEmpty()) {
            throw new CustomException("Water Body not found with ID: " + id);
        }
       
        return wOptional.get();
    }
    

    @Override
    public Boolean checkName(String name) throws CustomException {
         Optional<WaterBody> wOptional = waterBodyR.getWaterBodyByName(name);
         return wOptional.isPresent();
    }


    private WaterBodyDTO convertToDTO(WaterBody wB){
        return new WaterBodyDTO(wB.getId(), wB.getName(), wB.getBasin(), wB.getZone(), wB.getWaterQuality());
    }
}

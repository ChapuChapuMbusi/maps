package cartographish.maps.maps.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cartographish.maps.maps.dto.BasinDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.Basin;
import cartographish.maps.maps.repository.BasinRepository;
import cartographish.maps.maps.request.BasinRequest;
import cartographish.maps.maps.service.interfaces.IBasinService;

@Service
public class BasinServiceImpl implements IBasinService{

    @Autowired
    private BasinRepository basinR;
    @Override
    public List<BasinDTO> getAllZones() {
        return basinR.findAll().stream().map(basin -> new BasinDTO(basin.getBasinCode(), basin.getBasinName())).collect(Collectors.toList());
    }

    @Override
    public Basin getBasinById(Integer id) throws CustomException {
       Optional<Basin> bOptional = basinR.findById(id);
       if (bOptional.isEmpty()) {
           throw new CustomException("Basin not found with ID: " + id);
       }

       return bOptional.get();
    }

    @Override
    @Transactional
    public void createUpdateBasin(BasinRequest req) throws CustomException {
        Basin basin;
        if(req.getId() != null){
            Optional<Basin> bOptional = basinR.findById(req.getId());
             if(bOptional.isEmpty()){
                throw new CustomException("Unable to update: persistent basin");
            }
            basin = bOptional.get();
        } else {
            basin = new Basin();
        }

        basin.setBasinCode(req.getBasinCode());
        basin.setBasinName(req.getBasinName());

        basinR.save(basin);
    }

    @Override
    @Transactional
    public void deleteBasin(Integer id) throws CustomException {
        Optional<Basin> bOptional = basinR.findById(id);
        if(bOptional.isEmpty()){
            throw new CustomException("Basin not found with ID: " + id);
        }
        basinR.deleteById(bOptional.get().getId());
    }

}

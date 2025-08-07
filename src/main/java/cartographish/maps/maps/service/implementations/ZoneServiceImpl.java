package cartographish.maps.maps.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cartographish.maps.maps.dto.ZoneDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.models.Zone;
import cartographish.maps.maps.repository.ZoneRepository;
import cartographish.maps.maps.request.ZoneRequest;
import cartographish.maps.maps.service.interfaces.IZoneService;

@Service
public class ZoneServiceImpl implements IZoneService{

    @Autowired
    private ZoneRepository zoneR;
    @Override
    public List<ZoneDTO> getAllZones() {
        return zoneR.findAll().stream().map(zone -> new ZoneDTO(zone.getZoneCode())).collect(Collectors.toList());
    }

    @Override
    public Zone getZoneById(Integer id) throws CustomException {
        Optional<Zone> wOptional = zoneR.findById(id);

        if (wOptional.isEmpty()) {
            throw new CustomException("Zone not found with ID: " + id);
        }
       
        return wOptional.get();
    }

    @Override
    @Transactional
    public void createUpdateZone(ZoneRequest req) throws CustomException {
        Zone zone;
        if (req.getId() != null) {
            Optional<Zone> zOptional = zoneR.findById(req.getId());
            if (zOptional.isEmpty()) {
                throw new CustomException("Unable to update: persistent zone");
            }
            zone = zOptional.get();
        } else {
            zone = new Zone();
        }

        zone.setZoneCode(req.getZoneCode());
        zoneR.save(zone);
    }

    @Override
    @Transactional
    public void deleteZone(Integer id) throws CustomException {
       Optional<Zone> zOptional = zoneR.findById(id);
       if(zOptional.isEmpty()){
            throw new CustomException("Zone not found with ID: " + id);
        }
        zoneR.deleteById(zOptional.get().getId());
    }

}

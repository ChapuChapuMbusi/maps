package cartographish.maps.maps.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cartographish.maps.maps.models.Zone;

@Repository
public interface ZoneRepository extends MongoRepository<Zone, String>{

}

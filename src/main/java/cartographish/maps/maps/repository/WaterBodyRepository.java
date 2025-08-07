package cartographish.maps.maps.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cartographish.maps.maps.models.WaterBody;

@Repository
public interface WaterBodyRepository extends MongoRepository<WaterBody, String> {

    List<WaterBody> findByNameContainingIgnoreCase(String name);
   
}

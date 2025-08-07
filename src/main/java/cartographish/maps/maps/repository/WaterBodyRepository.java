package cartographish.maps.maps.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cartographish.maps.maps.models.WaterBody;

@Repository
public interface WaterBodyRepository extends MongoRepository<WaterBody, String> {

   
}

package cartographish.maps.maps.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cartographish.maps.maps.models.ContestoPescatore;

@Repository
public interface TesserinoContextRepository extends MongoRepository<ContestoPescatore, String> {

}

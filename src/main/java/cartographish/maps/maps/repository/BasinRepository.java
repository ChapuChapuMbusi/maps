package cartographish.maps.maps.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cartographish.maps.maps.models.Basin;


@Repository
public interface BasinRepository extends MongoRepository<Basin, String>{

}

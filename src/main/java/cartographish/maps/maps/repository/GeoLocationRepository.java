package cartographish.maps.maps.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cartographish.maps.maps.models.GeoLocation;


@Repository
public interface GeoLocationRepository extends MongoRepository<GeoLocation, String>{

}

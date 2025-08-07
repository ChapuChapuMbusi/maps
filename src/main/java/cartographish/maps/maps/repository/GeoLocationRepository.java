package cartographish.maps.maps.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cartographish.maps.maps.models.GeoLocation;
@Repository
public interface GeoLocationRepository extends MongoRepository<GeoLocation, Integer>{
    Optional<GeoLocation> findByLatitudineAndLongitudine(double latitudine, double longitudine);
}

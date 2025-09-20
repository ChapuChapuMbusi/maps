package cartographish.maps.maps.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cartographish.maps.maps.models.Basin;


@Repository
public interface BasinRepository extends MongoRepository<Basin, String>{

    Optional<Basin> findByBasinCode(String basinCode);
    Optional<Basin> findByBasinName(String basinName);
    boolean existsByBasinCode(String year);
}

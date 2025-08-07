package cartographish.maps.maps.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "basin")
public class Basin {

    @Id
    private Integer id;
    private String basinCode; //Codice del bacino ad esemp.(1, 2, 3, 4)
    private String basinName; //Nomde del bacino esem.(Po, Fiora, Tevere)
}

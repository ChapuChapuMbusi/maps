package cartographish.maps.maps.exception;

public class WaterBodyNotFoundException extends RuntimeException{

    public WaterBodyNotFoundException(String id){
        super("Water Body not found with ID: " + id);
    }
}

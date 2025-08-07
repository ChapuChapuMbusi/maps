package cartographish.maps.maps.exception;

public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;
    public CustomException(){
        super();
    }

    public CustomException(String msg){
        super(msg);
    }

}

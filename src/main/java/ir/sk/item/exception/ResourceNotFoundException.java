package ir.sk.item.exception;

/**
 * Created by sad.kayvanfar on 1/1/2022
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

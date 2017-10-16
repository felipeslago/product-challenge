package product.challenge.provider;

import product.challenge.exception.ImageNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * A provider for handling image not found exception.
 */
@Provider
public class ImageNotFoundExceptionHandler implements ExceptionMapper<ImageNotFoundException> {

    @Override
    public Response toResponse(ImageNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "The image with id: " + e.getId() + ", was not found.");
        return Response.status(404).entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}

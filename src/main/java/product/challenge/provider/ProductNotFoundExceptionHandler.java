package product.challenge.provider;

import product.challenge.exception.ProductNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * A provider for handling product not found exception.
 */
@Provider
public class ProductNotFoundExceptionHandler implements ExceptionMapper<ProductNotFoundException> {

    @Override
    public Response toResponse(ProductNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "The product with id:, " + e.getId() + " was not found.");
        return Response.status(404).entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}

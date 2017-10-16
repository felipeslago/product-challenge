package product.challenge.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import product.challenge.exception.ImageNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * A provider for handling product data integrity violation exception.
 */
@Provider
public class ProductIntegrityExceptionHandler implements ExceptionMapper<DataIntegrityViolationException> {

    private final Logger logger = LoggerFactory.getLogger(ProductIntegrityExceptionHandler.class);

    @Override
    public Response toResponse(DataIntegrityViolationException e) {
        logger.error(e.getMessage());

        Map<String, Object> response = new HashMap<>();

        if(e.getMessage().contains("PRODUCT FOREIGN KEY")) {
            response.put("message", "The requested product to be deleted has children.");
        }

        return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}

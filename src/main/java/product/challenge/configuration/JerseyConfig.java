package product.challenge.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import product.challenge.provider.ImageNotFoundExceptionHandler;
import product.challenge.provider.ProductIntegrityExceptionHandler;
import product.challenge.provider.ProductNotFoundExceptionHandler;
import product.challenge.resource.ImageResource;
import product.challenge.resource.ProductResource;

/**
 * A class for configuring Jersey Endpoints.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
    }

    /**
     * Register all Jersey resources.
     */
    private void registerEndpoints() {
        register(ProductResource.class);
        register(ImageResource.class);
        register(ImageNotFoundExceptionHandler.class);
        register(ProductNotFoundExceptionHandler.class);
        register(ProductIntegrityExceptionHandler.class);
    }

}

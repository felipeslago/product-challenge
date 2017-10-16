package product.challenge.exception;

/**
 * A custom exception for treating not found products.
 */
public class ProductNotFoundException extends RuntimeException {

    private Long id;

    public ProductNotFoundException(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}

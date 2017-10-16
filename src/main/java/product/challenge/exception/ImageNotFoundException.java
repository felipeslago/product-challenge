package product.challenge.exception;

/**
 * A custom exception for treating not found images.
 */
public class ImageNotFoundException extends RuntimeException {

    private Long id;

    public ImageNotFoundException(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}

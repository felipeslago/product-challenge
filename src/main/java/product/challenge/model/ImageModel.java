package product.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import product.challenge.entity.ImageEntity;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO representing an Image.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageModel implements Serializable {

    private static final long serialVersionUID = -8415005898287786364L;

    private Long id;
    private String type;

    private ImageModel() {
    }

    public ImageModel(ImageEntity imageEntity) {
        this.id = imageEntity.getId();
        this.type = imageEntity.getType();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ImageModel image = (ImageModel) obj;
        return Objects.equals(this.id, image.getId()) &&
                Objects.equals(this.type, image.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }

}

package product.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import product.challenge.entity.ImageEntity;
import product.challenge.entity.ProductEntity;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A DTO representing a Product.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductModel implements Serializable {

    private static final long serialVersionUID = 4079707674025020544L;

    private Long id;
    private String name;
    private String description;
    private ProductModel product;
    private Set<ImageModel> images;

    private ProductModel() {
    }

    public ProductModel(ProductEntity productEntity) {
        if(productEntity != null) {
            this.id = productEntity.getId();
            this.name = productEntity.getName();
            this.description = productEntity.getDescription();
        }
    }

    public void setProductWithProductAndImages(ProductEntity productEntity) {
        if(productEntity != null) {
            this.images = productEntity.getImageEntity().stream().map(ImageModel::new).collect(Collectors.toSet());
            this.product = new ProductModel(productEntity.getProductEntity());
        }
    }

    public void setProductWithImages(Set<ImageEntity> images) {
        if(images.size() > 0) {
            this.images = images.stream().map(ImageModel::new).collect(Collectors.toSet());
        }
    }

    public void setProductWithProduct(ProductEntity productEntity) {
        if(productEntity != null) {
            this.product = new ProductModel(productEntity.getProductEntity());
            this.product.setProductWithProduct(productEntity.getProductEntity());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ProductModel getProduct() {
        return product;
    }

    public Set<ImageModel> getImages() {
        return images;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductModel product = (ProductModel) obj;
        return Objects.equals(this.id, product.getId()) &&
                Objects.equals(this.name, product.getName()) &&
                Objects.equals(this.description, product.getDescription()) &&
                Objects.equals(this.images, product.getImages()) &&
                Objects.equals(this.product, product.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, images, description, name, id);
    }

}

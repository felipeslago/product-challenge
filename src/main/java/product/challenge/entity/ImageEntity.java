package product.challenge.entity;

import product.challenge.model.ImageModel;

import javax.persistence.*;

/**
 * An Image.
 */
@Entity
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    protected ImageEntity() {
    }

    public ImageEntity(ImageModel imageModel) {
        this.id = imageModel.getId();
        this.type = imageModel.getType();
    }

    public ImageEntity(ImageModel imageModel, ProductEntity productEntity) {
        this.id = imageModel.getId();
        this.type = imageModel.getType();
        this.productEntity = productEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}

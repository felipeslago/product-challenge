package product.challenge.entity;

import product.challenge.model.ProductModel;

import javax.persistence.*;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ImageEntity> imageEntity;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProductEntity productEntity;

    protected ProductEntity() {
    }

    public ProductEntity(ProductModel productModel) {
        if(productModel != null) {
            this.id = productModel.getId();
            this.name = productModel.getName();
            this.description = productModel.getDescription();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ImageEntity> getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(Set<ImageEntity> imageEntity) {
        this.imageEntity = imageEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

}

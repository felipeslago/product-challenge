package product.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.challenge.entity.ImageEntity;
import product.challenge.entity.ProductEntity;
import product.challenge.exception.ProductNotFoundException;
import product.challenge.model.ImageModel;
import product.challenge.model.ProductModel;
import product.challenge.repository.ImageRepository;
import product.challenge.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public ProductService(ProductRepository productRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * Retrieves all products.
     *
     * @return List<ProductModel>
     */
    public List<ProductModel> getProducts() {
        return productRepository.findAll().stream().map(ProductModel::new).collect(Collectors.toList());
    }

    /**
     * Retrieves all products with images.
     *
     * @return List<ProductModel>
     */
    @Transactional
    public List<ProductModel> getProductsWithImages() {
        return productRepository.findAll().stream().map(p -> {
            ProductModel productModel = new ProductModel(p);
            productModel.setProductWithImages(p.getImageEntity());
            return productModel;
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves all products with product.
     *
     * @return List<ProductModel>
     */
    @Transactional
    public List<ProductModel> getProductsWithProduct() {
        return productRepository.findAll().stream().map(p -> {
            ProductModel productModel = new ProductModel(p);
            productModel.setProductWithProduct(p);
            return productModel;
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves all products with product and images.
     *
     * @return List<ProductModel>
     */
    @Transactional
    public List<ProductModel> getProductsWithProductAndImages() {
        return productRepository.findAll().stream().map(p -> {
            ProductModel productModel = new ProductModel(p);
            productModel.setProductWithProductAndImages(p);
            return productModel;
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves a existing product by its id.
     *
     * @param id the product id
     * @return ProductModel
     */
    public ProductModel getProduct(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(id);

        if(productEntity.isPresent()) {
            return new ProductModel(productEntity.get());
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Retrieves a existing product by its id with images.
     *
     * @param id the product id
     * @return ProductModel
     */
    @Transactional
    public ProductModel getProductWithImages(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(id);

        if(productEntity.isPresent()) {
            ProductModel productModel =  new ProductModel(productEntity.get());
            productModel.setProductWithImages(productEntity.get().getImageEntity());
            return productModel;
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Retrieves a existing product by its id with images.
     *
     * @param id the product id
     * @return ProductModel
     */
    @Transactional
    public ProductModel getProductWithProduct(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(id);

        if(productEntity.isPresent()) {
            ProductModel productModel =  new ProductModel(productEntity.get());
            productModel.setProductWithProduct(productEntity.get());
            return productModel;
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Retrieves a existing product by its id with images.
     *
     * @param id the product id
     * @return ProductModel
     */
    @Transactional
    public ProductModel getProductWithProductAndImages(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(id);

        if(productEntity.isPresent()) {
            ProductModel productModel = new ProductModel(productEntity.get());
            productModel.setProductWithProductAndImages(productEntity.get());
            return productModel;
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Retrieves a set of child product by a product id.
     *
     * @param id the product id
     * @return ProductModel
     */
    @Transactional
    public ProductModel getChildProduct(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(id);

        if(productEntity.isPresent()) {
            ProductModel productModel = new ProductModel(productEntity.get().getProductEntity());
            productModel.setProductWithProduct(productEntity.get().getProductEntity());
            return productModel;
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Retrieves a set of child images by a product id.
     *
     * @param id the product id
     * @return Set<ImageModel>
     */
    @Transactional
    public Set<ImageModel> getChildImages(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(id);

        if(productEntity.isPresent()) {
            return productEntity.get().getImageEntity()
                    .stream().map(ImageModel::new).collect(Collectors.toSet());
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Register a new product.
     *
     * @param product the product to register
     */
    public void setProduct(ProductModel product) {
        ProductEntity productEntity = getProductEntity(product);
        productRepository.save(productEntity);
    }

    /**
     * Update a product.
     *
     * @param product the product to update
     */
    public void updateProduct(ProductModel product) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(product.getId());

        if(productEntity.isPresent()) {
            productRepository.save(productEntity.get());
        } else {
            throw new ProductNotFoundException(product.getId());
        }
    }

    /**
     * Delete a existing product.
     *
     * @param id the product id
     */
    public void deleteProduct(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findOneById(id);

        if(productEntity.isPresent()) {
            productRepository.delete(productEntity.get());
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    /**
     * Get a product entity recursively with relationships
     *
     * @param product the product model
     * @return ProductEntity
     */
    private ProductEntity getProductEntity(ProductModel product) {
        ProductEntity productEntity = new ProductEntity(product);

        Optional<Set<ImageModel>> images = Optional.ofNullable(product.getImages());

        if(images.isPresent()) {
            Set<ImageEntity> imageEntities = images.get().stream().map(i ->
                    new ImageEntity(i, productEntity)).collect(Collectors.toSet());

            productEntity.setImageEntity(imageEntities);
        }

        Optional.ofNullable(product.getProduct()).ifPresent(p ->
                productEntity.setProductEntity(getProductEntity(p)));

        return productEntity;
    }

}

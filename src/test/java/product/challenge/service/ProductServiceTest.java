package product.challenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.challenge.entity.ProductEntity;
import product.challenge.exception.ProductNotFoundException;
import product.challenge.model.ImageModel;
import product.challenge.model.ProductModel;
import product.challenge.repository.ProductRepository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    
    @InjectMocks
    private ProductService productService;
    
    @Mock
    private ProductRepository productRepository;

    private ClassLoader classLoader = this.getClass().getClassLoader();
    private ObjectMapper mapper = new ObjectMapper();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldReturnAllProductsExcludingRelationships() throws Exception {
        List<ProductEntity> products = getProducts("products.json");
        List<ProductModel> productsResponse = products.stream().map(ProductModel::new).collect(Collectors.toList());

        when(productRepository.findAll()).thenReturn(products);

        assertEquals(productsResponse, productService.getProducts());
    }

    @Test
    public void itShouldReturnAllProductsWithImagesRelationship() throws Exception {
        List<ProductEntity> products = getProducts("productsImage.json");
        List<ProductModel> expected = getProductsAsModel("productsImage.json");

        when(productRepository.findAll()).thenReturn(products);

        List<ProductModel> actual = productService.getProductsWithImages();

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void itShouldReturnAllProductsWithProductRelationship() throws Exception {
        List<ProductEntity> products = getProducts("productsProduct.json");
        List<ProductModel> expected = getProductsAsModel("productsProduct.json");

        when(productRepository.findAll()).thenReturn(products);

        List<ProductModel> actual = productService.getProductsWithProduct();

        assertEquals(expected, actual);
    }

    @Test
    public void itShouldReturnAllProductsWithProductAndImagesRelationships() throws Exception {
        List<ProductEntity> products = getProducts("productsProductImages.json");
        List<ProductModel> expected = getProductsAsModel("productsProductImages.json");

        when(productRepository.findAll()).thenReturn(products);

        List<ProductModel> actual = productService.getProductsWithProductAndImages();

        assertEquals(expected, actual);
    }

    @Test
    public void itShouldReturnASpecificProduct() throws Exception {
        ProductEntity product = getProduct("product.json");
        ProductModel productResponse = getProductAsModel("product.json");

        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));

        assertEquals(productResponse, productService.getProduct(1L));
    }

    @Test
    public void itShouldReturnASpecificProductWithImagesRelationship() throws Exception {
        ProductEntity product = getProduct("productImage.json");
        ProductModel expected = getProductAsModel("productImage.json");

        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));

        ProductModel actual = productService.getProductWithImages(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void itShouldReturnASpecificProductWithProductRelationship() throws Exception {
        ProductEntity product = getProduct("productProduct.json");
        ProductModel expected = getProductAsModel("productProduct.json");

        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));

        ProductModel actual = productService.getProductWithProduct(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void itShouldReturnASpecificProductWithProductAndImagesRelationship() throws Exception {
        ProductEntity product = getProduct("productProductImages.json");
        ProductModel expected = getProductAsModel("productProductImages.json");

        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));

        ProductModel actual = productService.getProductWithProductAndImages(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void itShouldReturnASetOfProduct() throws Exception {
        ProductEntity product = getProduct("childProduct.json");
        ProductModel expected = getProductAsModel("childProduct.json");

        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));

        ProductModel actual = productService.getChildProduct(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void itShouldReturnASetOfImages() throws Exception {
        ProductEntity product = getProduct("productProductImages.json");
        Set<ImageModel> expected = getImages("childImages.json");

        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));

        Set<ImageModel> actual = productService.getChildImages(1L);

        MatcherAssert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    public void itShouldRegisterAProduct() throws Exception {
        ProductModel product = getProductAsModel("productProductImages.json");
        productService.setProduct(product);
    }

    @Test
    public void itShouldUpdateAProduct() throws Exception {
        ProductEntity product = getProduct("product.json");
        ProductModel productRequest = getProductAsModel("product.json");
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));
        productService.updateProduct(productRequest);
    }

    @Test
    public void itShouldDeleteAProduct() throws Exception {
        ProductEntity product = getProduct("product.json");
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.of(product));
        productService.deleteProduct(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToReturnASpecificProductAndThrowProductNotFoundException() throws Exception {
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.getProduct(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToReturnASpecificProductWithImagesRelationshipAndThrowProductNotFoundException() throws Exception {
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.getProductWithImages(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToReturnASpecificProductWithProductRelationshipAndThrowProductNotFoundException() throws Exception {
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.getProductWithProduct(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToReturnASpecificProductWithProductAndImagesRelationshipAndThrowProductNotFoundException() throws Exception {
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.getProductWithProductAndImages(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToReturnASetOfProductAndThrowProductNotFoundException() throws Exception {
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.getChildProduct(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToReturnASetOfImagesAndThrowProductNotFoundException() throws Exception {
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.getChildImages(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToUpdateAProductAndThrowProductNotFoundException() throws Exception {
        ProductModel productModel = getProductAsModel("product.json");
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.updateProduct(productModel);
    }

    @Test(expected = ProductNotFoundException.class)
    public void itShouldTryToDeleteAProductAndThrowProductNotFoundException() throws Exception {
        when(productRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        productService.deleteProduct(1L);
    }

    private List<ProductEntity> getProducts(String path) throws Exception {
        File file = new File(classLoader.getResource("entity/" + path).getFile());
        ProductEntity[] products = mapper.readValue(file, ProductEntity[].class);
        return Arrays.asList(products);
    }

    private ProductEntity getProduct(String path) throws Exception {
        File file = new File(classLoader.getResource("entity/" + path).getFile());
        ProductEntity products = mapper.readValue(file, ProductEntity.class);
        return products;
    }

    private List<ProductModel> getProductsAsModel(String path) throws Exception {
        File file = new File(classLoader.getResource("model/" + path).getFile());
        ProductModel[] products = mapper.readValue(file, ProductModel[].class);
        return Arrays.asList(products);
    }

    private ProductModel getProductAsModel(String path) throws Exception {
        File file = new File(classLoader.getResource("model/" + path).getFile());
        ProductModel product = mapper.readValue(file, ProductModel.class);
        return product;
    }

    private Set<ImageModel> getImages(String path) throws Exception {
        File file = new File(classLoader.getResource("model/" + path).getFile());
        ImageModel[] images = mapper.readValue(file, ImageModel[].class);
        return Arrays.stream(images).collect(Collectors.toSet());
    }

}
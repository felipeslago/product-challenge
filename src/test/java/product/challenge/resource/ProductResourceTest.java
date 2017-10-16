package product.challenge.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.challenge.model.ImageModel;
import product.challenge.model.ProductModel;
import product.challenge.service.ProductService;

import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ProductResourceTest {

    @InjectMocks
    private ProductResource productResource;

    @Mock
    private ProductService productService;

    private ClassLoader classLoader = this.getClass().getClassLoader();
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldReturnAllProductsExcludingRelationships() throws Exception {
        List<ProductModel> products = getProducts("products.json");
        String json = mapper.writeValueAsString(products);

        when(productService.getProducts()).thenReturn(products);

        Response response = productResource.getProducts();
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnAllProductsWithImagesRelationship() throws Exception {
        List<ProductModel> products = getProducts("productsImage.json");
        String json = mapper.writeValueAsString(products);

        when(productService.getProductsWithImages()).thenReturn(products);

        Response response = productResource.getProductsWithImages();
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnAllProductsWithProductRelationship() throws Exception {
        List<ProductModel> products = getProducts("productsProduct.json");
        String json = mapper.writeValueAsString(products);

        when(productService.getProductsWithProduct()).thenReturn(products);

        Response response = productResource.getProductsWithProduct();
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnAllProductsWithProductAndImagesRelationships() throws Exception {
        List<ProductModel> products = getProducts("productsProductImages.json");
        String json = mapper.writeValueAsString(products);

        when(productService.getProductsWithProductAndImages()).thenReturn(products);

        Response response = productResource.getProductsWithProductAndImages();
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnASpecificProduct() throws Exception {
        ProductModel product = getProduct("product.json");
        String json = mapper.writeValueAsString(product);

        when(productService.getProduct(anyLong())).thenReturn(product);

        Response response = productResource.getProduct(1L);
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnASpecificProductWithImagesRelationship() throws Exception {
        ProductModel product = getProduct("productImage.json");
        String json = mapper.writeValueAsString(product);

        when(productService.getProductWithImages(anyLong())).thenReturn(product);

        Response response = productResource.getProductWithImages(1L);
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnASpecificProductWithProductRelationship() throws Exception {
        ProductModel product = getProduct("productProduct.json");
        String json = mapper.writeValueAsString(product);

        when(productService.getProductWithProduct(anyLong())).thenReturn(product);

        Response response = productResource.getProductWithProduct(1L);
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnASpecificProductWithProductAndImagesRelationship() throws Exception {
        ProductModel product = getProduct("productProductImages.json");
        String json = mapper.writeValueAsString(product);

        when(productService.getProductWithProductAndImages(anyLong())).thenReturn(product);

        Response response = productResource.getProductWithProductAndImages(1L);
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnASetOfProduct() throws Exception {
        ProductModel product = getProduct("childProduct.json");
        String json = mapper.writeValueAsString(product);

        when(productService.getChildProduct(anyLong())).thenReturn(product);

        Response response = productResource.getChildProduct(1L);
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnASetOfImages() throws Exception {
        Set<ImageModel> images = getImages("childImages.json");
        String json = mapper.writeValueAsString(images);

        when(productService.getChildImages(anyLong())).thenReturn(images);

        Response response = productResource.getChildImages(1L);
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldRegisterAProduct() throws Exception {
        ProductModel product = getProduct("product.json");
        String json = mapper.writeValueAsString(product);

        doNothing().when(productService).setProduct(any(ProductModel.class));

        Response response = productResource.setProduct(product);

        assertEquals(201, response.getStatus());
        assertEquals(null, response.getEntity());
    }

    @Test
    public void itShouldUpdateAProduct() throws Exception {
        ProductModel product = getProduct("product.json");
        String json = mapper.writeValueAsString(product);

        doNothing().when(productService).updateProduct(any(ProductModel.class));

        Response response = productResource.updateProduct(product);

        assertEquals(204, response.getStatus());
        assertEquals(null, response.getEntity());
    }

    @Test
    public void itShouldDeleteAProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        Response response = productResource.deleteProduct(1L);

        assertEquals(204, response.getStatus());
        assertEquals(null, response.getEntity());
    }

    private List<ProductModel> getProducts(String path) throws Exception {
        File file = new File(classLoader.getResource("model/" + path).getFile());
        ProductModel[] products = mapper.readValue(file, ProductModel[].class);
        return Arrays.asList(products);
    }

    private ProductModel getProduct(String path) throws Exception {
        File file = new File(classLoader.getResource("model/" + path).getFile());
        ProductModel products = mapper.readValue(file, ProductModel.class);
        return products;
    }

    private Set<ImageModel> getImages(String path) throws Exception {
        File file = new File(classLoader.getResource("model/" + path).getFile());
        ImageModel[] images = mapper.readValue(file, ImageModel[].class);
        return Arrays.stream(images).collect(Collectors.toSet());
    }

}
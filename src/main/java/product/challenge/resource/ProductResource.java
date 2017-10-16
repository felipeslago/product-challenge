package product.challenge.resource;

import org.springframework.stereotype.Component;
import product.challenge.model.ImageModel;
import product.challenge.model.ProductModel;
import product.challenge.service.ProductService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

/**
 * REST resource for managing products.
 * <p>
 * This class accesses the Product service.
 */
@Component
@Path("/product")
public class ProductResource {

    private ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET  /product  : Retrieves all products.
     *
     * @return the Response with status 200 (OK) and with body the list of products
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        List<ProductModel> products = productService.getProducts();
        return Response.status(200).entity(products).build();
    }

    /**
     * GET  /product/images  : Retrieves all products with images relationship.
     *
     * @return the Response with status 200 (OK) and with body the list of products
     */
    @GET
    @Path("/images")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsWithImages() {
        List<ProductModel> products = productService.getProductsWithImages();
        return Response.status(200).entity(products).build();
    }

    /**
     * GET  /product/product  : Retrieves all products with product relationship.
     *
     * @return the Response with status 200 (OK) and with body the list of products
     */
    @GET
    @Path("/product")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsWithProduct() {
        List<ProductModel> products = productService.getProductsWithProduct();
        return Response.status(200).entity(products).build();
    }

    /**
     * GET  /product/product/images  : Retrieves all products with product and images relationships.
     *
     * @return the Response with status 200 (OK) and with body the list of products
     */
    @GET
    @Path("/product/images")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsWithProductAndImages() {
        List<ProductModel> products = productService.getProductsWithProductAndImages();
        return Response.status(200).entity(products).build();
    }

    /**
     * GET  /product/{id}  : Retrieves a product by its id.
     * * <p>
     * Retrieve a existing product if the product id exists
     *
     * @param id the product id
     * @return the Response with status 200 (OK) and with body the requested product
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") final Long id) {
        ProductModel product = productService.getProduct(id);
        return Response.ok().entity(product).build();
    }

    /**
     * GET  /product/{id}/images  : Retrieves a product by its id with images.
     * * <p>
     * Retrieve a existing product with images relationship if the product id exists
     *
     * @param id the product id
     * @return the Response with status 200 (OK) and with body the requested product
     */
    @GET
    @Path("/{id}/images")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductWithImages(@PathParam("id") final Long id) {
        ProductModel product = productService.getProductWithImages(id);
        return Response.ok().entity(product).build();
    }

    /**
     * GET  /product/{id}/product  : Retrieves a product by its id with product.
     * * <p>
     * Retrieve a existing product with product relationship if the product id exists
     *
     * @param id the product id
     * @return the Response with status 200 (OK) and with body the requested product
     */
    @GET
    @Path("/{id}/product")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductWithProduct(@PathParam("id") final Long id) {
        ProductModel product = productService.getProductWithProduct(id);
        return Response.ok().entity(product).build();
    }

    /**
     * GET  /product/{id}/product/images  : Retrieves a product by its id with product.
     * * <p>
     * Retrieve a existing product with product and images relationships if the product id exists
     *
     * @param id the product id
     * @return the Response with status 200 (OK) and with body the requested product
     */
    @GET
    @Path("/{id}/product/images")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductWithProductAndImages(@PathParam("id") final Long id) {
        ProductModel product = productService.getProductWithProductAndImages(id);
        return Response.ok().entity(product).build();
    }

    /**
     * GET  /product/{id}/product/child  : Retrieves a set of child product by a product id.
     *
     * @param id the product id
     * @return the Response with status 200 (OK) and with body the requested set of product
     */
    @GET
    @Path("/{id}/product/child")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChildProduct(@PathParam("id") final Long id) {
        ProductModel product = productService.getChildProduct(id);
        return Response.ok().entity(product).build();
    }

    /**
     * GET  /product/{id}/images/child  : Retrieves a set of child images by a product id.
     *
     * @param id the product id
     * @return the Response with status 200 (OK) and with body the requested set of images
     */
    @GET
    @Path("/{id}/images/child")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChildImages(@PathParam("id") final Long id) {
        Set<ImageModel> images = productService.getChildImages(id);
        return Response.ok().entity(images).build();
    }

    /**
     * POST  /product  : Register a new product.
     *
     * @return the Response with status 200 (OK) and with null body
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setProduct(ProductModel product) {
        productService.setProduct(product);
        return Response.status(201).build();
    }

    /**
     * PUT  /product  : Update a existing product.
     *
     * @return the Response with status 204 (NO CONTENT) and with null body
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(ProductModel product) {
        productService.updateProduct(product);
        return Response.status(204).build();
    }

    /**
     * DELETE  /product/{id}  : Delete a existing product.
     * <p>
     * Delete a existing product if the product id exists
     *
     * @return the Response with status 204 (NO CONTENT) and with null body
     */
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") final Long id) {
        productService.deleteProduct(id);
        return Response.status(204).build();
    }

}

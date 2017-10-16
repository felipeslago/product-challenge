package product.challenge.resource;

import org.springframework.stereotype.Component;
import product.challenge.model.ImageModel;
import product.challenge.service.ImageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * REST resource for managing images.
 * <p>
 * This class accesses the Image service.
 */
@Component
@Path("/image")
public class ImageResource {

    private ImageService imageService;

    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * GET  /image  : Retrieves all images.
     *
     * @return the Response with status 200 (OK) and with body the list of images
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImages() {
        List<ImageModel> images = imageService.getImages();
        return Response.status(200).entity(images).build();
    }

    /**
     * GET  /image/{id}  : Retrieves an image by its id.
     * * <p>
     * Retrieve an existing image if the image id exists
     *
     * @param id the image id
     * @return the Response with status 200 (OK) and with body the requested image
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImage(@PathParam("id") final Long id) {
        ImageModel image = imageService.getImage(id);
        return Response.ok().entity(image).build();
    }

    /**
     * POST  /image  : Register an new image.
     *
     * @return the Response with status 200 (OK) and with null body
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setImage(ImageModel image) {
        imageService.setImage(image);
        return Response.status(201).build();
    }

    /**
     * PUT  /image  : Update an existing image.
     *
     * @return the Response with status 204 (NO CONTENT) and with null body
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateImage(ImageModel image) {
        imageService.updateImage(image);
        return Response.status(204).build();
    }

    /**
     * DELETE  /image/{id}  : Delete an existing image.
     * <p>
     * Delete an existing image if the image id exists
     *
     * @return the Response with status 204 (NO CONTENT) and with null body
     */
    @DELETE
    @Path("/{id}")
    public Response deleteImage(@PathParam("id") final Long id) {
        imageService.deleteImage(id);
        return Response.status(204).build();
    }

}

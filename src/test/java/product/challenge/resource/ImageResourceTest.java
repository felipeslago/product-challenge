package product.challenge.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.challenge.model.ImageModel;
import product.challenge.service.ImageService;

import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ImageResourceTest {

    @InjectMocks
    private ImageResource imageResource;

    @Mock
    private ImageService imageService;

    private ClassLoader classLoader = this.getClass().getClassLoader();
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldReturnAllImages() throws Exception {
        List<ImageModel> images = getSetOfImages();
        String json = mapper.writeValueAsString(images);

        when(imageService.getImages()).thenReturn(images);

        Response response = imageResource.getImages();
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldReturnAnSpecificImage() throws Exception {
        ImageModel image = getImage();
        String json = mapper.writeValueAsString(image);

        when(imageService.getImage(anyLong())).thenReturn(image);

        Response response = imageResource.getImage(1L);
        String jsonResponse = mapper.writeValueAsString(response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(json, jsonResponse);
    }

    @Test
    public void itShouldRegisterAnImage() throws Exception {
        ImageModel image = getImage();

        doNothing().when(imageService).setImage(any(ImageModel.class));

        Response response = imageResource.setImage(image);

        assertEquals(201, response.getStatus());
        assertEquals(null, response.getEntity());
    }

    @Test
    public void itShouldUpdateAnImage() throws Exception {
        ImageModel image = getImage();

        doNothing().when(imageService).updateImage(any(ImageModel.class));

        Response response = imageResource.updateImage(image);

        assertEquals(204, response.getStatus());
        assertEquals(null, response.getEntity());
    }

    @Test
    public void itShouldDeleteAnImage() throws Exception {
        doNothing().when(imageService).deleteImage(anyLong());

        Response response = imageResource.deleteImage(1L);

        assertEquals(204, response.getStatus());
        assertEquals(null, response.getEntity());
    }

    private List<ImageModel> getSetOfImages() throws Exception {
        File file = new File(classLoader.getResource("model/images.json").getFile());
        ImageModel[] images = mapper.readValue(file, ImageModel[].class);
        return Arrays.asList(images);
    }

    private ImageModel getImage() throws Exception {
        File file = new File(classLoader.getResource("model/image.json").getFile());
        ImageModel image = mapper.readValue(file, ImageModel.class);
        return image;
    }

}
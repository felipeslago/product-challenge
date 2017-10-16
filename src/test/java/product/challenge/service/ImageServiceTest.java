package product.challenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.challenge.entity.ImageEntity;
import product.challenge.exception.ImageNotFoundException;
import product.challenge.model.ImageModel;
import product.challenge.repository.ImageRepository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

public class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepository;

    private ClassLoader classLoader = this.getClass().getClassLoader();
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldGetAllImages() throws Exception {
        List<ImageEntity> images = getSetOfImages();
        List<ImageModel> imagesResponse = images.stream().map(ImageModel::new).collect(Collectors.toList());

        when(imageRepository.findAll()).thenReturn(images);

        assertEquals(imagesResponse, imageService.getImages());
    }

    @Test
    public void itShouldGetAnSpecificImage() throws Exception {
        ImageEntity image = getImage();
        ImageModel imageResponse = new ImageModel(image);

        when(imageRepository.findOneById(anyLong())).thenReturn(Optional.of(image));

        assertEquals(imageResponse, imageService.getImage(1L));
    }

    @Test
    public void itShouldRegisterAnImage() throws Exception {
        ImageModel image = new ImageModel(getImage());
        imageService.setImage(image);
    }

    @Test
    public void itShouldUpdateAnImage() throws Exception {
        ImageEntity image = getImage();
        ImageModel imageResponse = new ImageModel(getImage());

        when(imageRepository.findOneById(anyLong())).thenReturn(Optional.of(image));

        imageService.updateImage(imageResponse);
    }

    @Test
    public void itShouldDeleteAnImage() throws Exception {
        ImageEntity image = getImage();

        when(imageRepository.findOneById(anyLong())).thenReturn(Optional.of(image));

        imageService.deleteImage(1L);
    }

    @Test(expected = ImageNotFoundException.class)
    public void itShouldTryToGetAnSpecificImageAndThrowImageNotFoundException() throws Exception {
        when(imageRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        imageService.getImage(1L);
    }

    @Test(expected = ImageNotFoundException.class)
    public void itShouldTryToUpdateAnImageAndThrowImageNotFoundException() throws Exception {
        ImageModel imageModel = new ImageModel(getImage());
        when(imageRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        imageService.updateImage(imageModel);
    }

    @Test(expected = ImageNotFoundException.class)
    public void itShouldTryToDeleteAnImageAndThrowImageNotFoundException() throws Exception {
        when(imageRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        imageService.deleteImage(1L);
    }

    private List<ImageEntity> getSetOfImages() throws Exception {
        File file = new File(classLoader.getResource("entity/images.json").getFile());
        ImageEntity[] images = mapper.readValue(file, ImageEntity[].class);
        return Arrays.asList(images);
    }

    private ImageEntity getImage() throws Exception {
        File file = new File(classLoader.getResource("entity/image.json").getFile());
        ImageEntity image = mapper.readValue(file, ImageEntity.class);
        return image;
    }

}
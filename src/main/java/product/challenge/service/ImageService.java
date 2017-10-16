package product.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.challenge.entity.ImageEntity;
import product.challenge.exception.ImageNotFoundException;
import product.challenge.model.ImageModel;
import product.challenge.repository.ImageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing images.
 */
@Service
@Transactional
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Retrieves all images.
     *
     * @return List<ImageModel>
     */
    public List<ImageModel> getImages() {
        return imageRepository.findAll().stream().map(ImageModel::new).collect(Collectors.toList());
    }

    /**
     * Retrieves an existing image by its id.
     *
     * @param id the image id
     * @return ImageModel
     */
    public ImageModel getImage(Long id) {
        Optional<ImageEntity> imageEntity = imageRepository.findOneById(id);

        if(imageEntity.isPresent()) {
            return new ImageModel(imageEntity.get());
        } else {
            logger.info("Image with id:={} was not found", id);
            throw new ImageNotFoundException(id);
        }
    }

    /**
     * Register an new image.
     *
     * @param image the image to register
     */
    public void setImage(ImageModel image) {
        imageRepository.save(new ImageEntity(image));
    }

    /**
     * Update an image.
     *
     * @param image the image to update
     */
    public void updateImage(ImageModel image) {
        Optional<ImageEntity> imageEntity = imageRepository.findOneById(image.getId());

        if(imageEntity.isPresent()) {
            imageEntity.get().setType(image.getType());
            imageRepository.save(imageEntity.get());
        } else {
            logger.info("Image with id:={} was not found", image.getId());
            throw new ImageNotFoundException(image.getId());
        }
    }

    /**
     * Delete an existing image.
     *
     * @param id the image id
     */
    public void deleteImage(Long id) {
        Optional<ImageEntity> imageEntity = imageRepository.findOneById(id);

        if(imageEntity.isPresent()) {
            imageRepository.delete(imageEntity.get());
        } else {
            logger.info("Image with id:={} was not found", id);
            throw new ImageNotFoundException(id);
        }
    }
}

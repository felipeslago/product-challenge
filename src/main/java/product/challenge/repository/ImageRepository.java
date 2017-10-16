package product.challenge.repository;

import org.springframework.data.repository.CrudRepository;
import product.challenge.entity.ImageEntity;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data CRUD repository for the Image entity.
 */

public interface ImageRepository extends CrudRepository<ImageEntity, Long> {

    List<ImageEntity> findAll();

    Optional<ImageEntity> findOneById(Long id);

}

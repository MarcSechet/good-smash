package mic.poulet.goodsmash.smash.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.smash.model.ImageType;
import mic.poulet.goodsmash.smash.model.MyImage;

public interface ImageDao extends JpaRepository<MyImage, Long> {
    Optional<MyImage> findByCharacterIdAndType(Long characterId, ImageType type);
}

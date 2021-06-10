package mic.poulet.goodsmash.smash.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mic.poulet.goodsmash.smash.model.information.Information;
import mic.poulet.goodsmash.smash.model.information.InformationType;

public interface InformationDao extends JpaRepository<Information, Long> {

    Information findByDescriptionAndInformationType(String description, InformationType informationType);

    Information findByTargetId(Long targetId);
}

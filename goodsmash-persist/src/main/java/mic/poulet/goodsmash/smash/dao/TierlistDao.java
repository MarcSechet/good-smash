package mic.poulet.goodsmash.smash.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mic.poulet.goodsmash.smash.model.tierlist.Tierlist;


public interface TierlistDao extends JpaRepository<Tierlist, Long> {

    @Modifying
    @Query(value = "DELETE FROM tier_character_ids WHERE character_ids = ?1 ",
            nativeQuery = true)
    void deleteCharacterIdFromAllTiers(@Param("characterId") Long characterId);

    @Modifying
    @Query(value = "DELETE FROM tierlist_unused_character_ids WHERE unused_character_ids = ?1 ",
            nativeQuery = true)
    void deleteCharacterIdFromUnusedList(@Param("characterId") Long characterId);

}

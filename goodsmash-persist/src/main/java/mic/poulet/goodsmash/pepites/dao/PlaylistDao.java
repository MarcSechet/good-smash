package mic.poulet.goodsmash.pepites.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.pepites.model.Playlist;

public interface PlaylistDao extends JpaRepository<Playlist, Long> {
	Optional<Playlist> findByPlaylistId(String playlistId);

	List<Playlist> findByUploaderName(String uploaderName);
}

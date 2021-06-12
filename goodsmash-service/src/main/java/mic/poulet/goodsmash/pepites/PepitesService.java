package mic.poulet.goodsmash.pepites;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.pepites.dao.PlaylistDao;
import mic.poulet.goodsmash.pepites.model.Playlist;

@AllArgsConstructor
@Service
public class PepitesService {

	private final PlaylistDao playlistDao;

	public Optional<Playlist> findByPlaylistId(String playlistId) {
		return playlistDao.findByPlaylistId(playlistId);
	}

	public List<Playlist> getAll(String uploaderName) {

		return StringUtils.isEmpty(uploaderName)
				? playlistDao.findAll()
				: playlistDao.findByUploaderName(uploaderName);
	}

	public Playlist create(Playlist playlist) {
		return playlistDao.save(playlist);
	}

	public Playlist save(Playlist playlist) {
		return playlistDao.save(playlist);
	}

	public Playlist update(Playlist playlist) {
		// TODO : put pojo here for update
		return playlistDao.save(playlist);
	}

	public void delete(Playlist playlist) {
		playlistDao.delete(playlist);
	}
}

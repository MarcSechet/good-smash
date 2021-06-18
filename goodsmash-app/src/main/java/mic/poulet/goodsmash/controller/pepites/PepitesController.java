package mic.poulet.goodsmash.controller.pepites;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.pepites.PepitesService;
import mic.poulet.goodsmash.pepites.model.Playlist;
import mic.poulet.goodsmash.pepites.model.Timecode;
import mic.poulet.goodsmash.spec.api.PepitesApi;
import mic.poulet.goodsmash.spec.model.PlaylistDto;
import mic.poulet.goodsmash.spec.model.TimecodeDto;

@AllArgsConstructor
@RestController
@RequestMapping("${api-smash.base-path:}")
public class PepitesController implements PepitesApi {

	private final PepitesService pepitesService;
	private final ModelMapper mapper;

	private PlaylistDto toDto(Playlist playlist) {
		return mapper.map(playlist, PlaylistDto.class);
	}

	@Override
	public ResponseEntity<List<PlaylistDto>> getAllPlaylists(String uploaderName) {
		List<Playlist> playlists = pepitesService.getAll(uploaderName);
		return CollectionUtils.isEmpty(playlists) ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(playlists.stream().map(this::toDto).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<PlaylistDto> createPlaylist(PlaylistDto playlistDto) {
		Playlist playlist = pepitesService.create(mapper.map(playlistDto, Playlist.class));
		return ResponseEntity.ok(toDto(playlist));
	}

	@Override
	public ResponseEntity<PlaylistDto> getByPlaylistId(String playlistId) {
		Playlist playlist = pepitesService.findByPlaylistId(playlistId).orElseThrow();
		return ResponseEntity.ok(toDto(playlist));
	}

	@Override
	public ResponseEntity<PlaylistDto> updateByPlaylistId(String playlistId, PlaylistDto playlistDto) {
		Playlist playlist = pepitesService.findByPlaylistId(playlistId).orElseThrow();

		Optional.ofNullable(playlistDto.getIsPublic())
				.ifPresent(playlist::setIsPublic);

		playlist = pepitesService.update(playlist);
		return ResponseEntity.ok(toDto(playlist));
	}

	@Override
	public ResponseEntity<Void> deleteByPlaylistId(String playlistId) {
		Playlist playlist = pepitesService.findByPlaylistId(playlistId).orElseThrow();
		pepitesService.delete(playlist);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<PlaylistDto> addTimeCodeInPlaylist(String playlistId, TimecodeDto timecodeDto) {
		Playlist playlist = pepitesService.findByPlaylistId(playlistId).orElseThrow();
		playlist.getTimecodes().add(mapper.map(timecodeDto, Timecode.class));
		playlist = pepitesService.save(playlist);
		return ResponseEntity.ok(toDto(playlist));
	}

	@Override
	public ResponseEntity<Void> deleteTimecodeById(String playlistId, Long id) {
		Playlist playlist = pepitesService.findByPlaylistId(playlistId).orElseThrow();
		playlist.getTimecodes().removeIf(timecode -> timecode.getId().equals(id));
		pepitesService.save(playlist);
		return ResponseEntity.ok().build();
	}
}

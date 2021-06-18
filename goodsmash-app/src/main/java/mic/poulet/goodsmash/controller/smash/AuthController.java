package mic.poulet.goodsmash.controller.smash;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.AuthService;
import mic.poulet.goodsmash.smash.model.auth.SmashUser;
import mic.poulet.goodsmash.spec.api.AuthApi;
import mic.poulet.goodsmash.spec.model.UserDto;

@AllArgsConstructor
@RestController
@RequestMapping("${api-smash.base-path:}")
public class AuthController implements AuthApi {

	private final AuthService authService;
	private final ModelMapper mapper;

	private UserDto toDto(SmashUser user) {
		return mapper.map(user, UserDto.class);
	}

	@Override
	public ResponseEntity<UserDto> login(UserDto userDto) {
		Optional<SmashUser> user = authService.findByEmail(userDto.getEmail());

		if (user.isPresent()) {
			//user.setToken(userDto.getToken());
			authService.save(user.get());
			return ResponseEntity.ok(toDto(user.get()));
		} else {
			SmashUser newUser = new SmashUser();
			newUser.setIsApproved(false);
			newUser.setEmail(userDto.getEmail());
			authService.save(newUser);
			return ResponseEntity.ok(toDto(newUser));
		}
	}

	@Override
	public ResponseEntity<UserDto> logout(UserDto userDto) {
		SmashUser smashUser = authService.findByEmail(userDto.getEmail()).orElseThrow();

		if (smashUser != null) {
			//userDtoDb.setToken(null);
			// authRepository.delete(userDtoDb); // TODO : remove the delete & do a real operation
			return ResponseEntity.ok(toDto(smashUser));
		}
		return ResponseEntity.badRequest().build();
	}
}

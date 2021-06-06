package mic.poulet.goodsmash.configuration;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mic.poulet.goodsmash.smash.model.MyImage;
import mic.poulet.goodsmash.smash.model.auth.SmashUser;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.spec.model.CharacterDto;
import mic.poulet.goodsmash.spec.model.UserDto;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper mapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper;
	}
}

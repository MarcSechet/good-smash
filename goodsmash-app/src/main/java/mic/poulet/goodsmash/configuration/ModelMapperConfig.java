package mic.poulet.goodsmash.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.confirm.Confirm;
import mic.poulet.goodsmash.spec.model.CharacterDto;
import mic.poulet.goodsmash.spec.model.ConfirmDto;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper mapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);

		modelMapper.typeMap(Character.class, CharacterDto.class)
				.addMapping(Character::isFloaty, CharacterDto::setIsFloaty);
		modelMapper.typeMap(CharacterDto.class, Character.class)
				.addMapping(CharacterDto::getIsFloaty, Character::setFloaty);
		modelMapper.typeMap(Confirm.class, ConfirmDto.class)
				.addMapping(Confirm::getConfirmDetails, ConfirmDto::setConfirmDetailsDtos);
		modelMapper.typeMap(ConfirmDto.class, Confirm.class)
				.addMapping(ConfirmDto::getConfirmDetailsDtos, Confirm::setConfirmDetails);

		return modelMapper;
	}
}

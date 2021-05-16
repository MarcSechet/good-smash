package mic.poulet.goodsmash.smash.model.character;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharacterWeight {

    @JsonAlias({"H", "HEAVY"})
    HEAVY("H"),

    @JsonAlias({"M", "MEDIUM"})
    MEDIUM("M"),

    @JsonAlias({"L", "LIGHT"})
    LIGHT("L");

    private final String weight;
}

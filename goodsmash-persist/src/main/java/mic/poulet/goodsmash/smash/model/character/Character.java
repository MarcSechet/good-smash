package mic.poulet.goodsmash.smash.model.character;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import mic.poulet.goodsmash.smash.model.combo.Combo;
import mic.poulet.goodsmash.smash.model.confirm.Confirm;
import mic.poulet.goodsmash.smash.model.information.Information;
import mic.poulet.goodsmash.smash.model.move.BestMove;
import mic.poulet.goodsmash.smash.model.tierlist.Tierlist;

@Entity
@Getter
@Setter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String tier;
    private CharacterWeight characterWeight;
    private boolean isFloaty;

    @ElementCollection
    private List<String> additionalFilters; // ex : deep breath for WFT, luma, nana, etc...

    @Column(columnDefinition = "integer default 0")
    private Integer skillRating; // from 1 to 5, 0 means not rated

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "character_id")
    private List<Information> informations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "character_id")
    private List<BestMove> bestMoves = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "character_id")
    private List<Confirm> confirms = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "character_id")
    private List<Combo> combos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "character_id")
    private List<Tierlist> tierlists = new ArrayList<>();

    public Character() {
    }
}

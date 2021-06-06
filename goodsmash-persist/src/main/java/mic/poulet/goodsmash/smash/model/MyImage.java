package mic.poulet.goodsmash.smash.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data;

    private Long characterId;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    public MyImage() {
    }

    public MyImage(byte[] data, Long characterId, ImageType type) {
        this.data = data;
        this.characterId = characterId;
        this.type = type;
    }

}

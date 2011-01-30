package example.om;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@GenericGenerator(
        name = "ID_GEN",
        strategy =  "native",
        parameters = {@Parameter(name="sequence",
                                 value= "id_seq")})
public class EntityA {
    @Id
    @GeneratedValue(generator="ID_GEN")
    private Long id;
    private Integer authorId;
    private String text;

    public EntityA() { }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public void setText(String text) {
        this.text = text;
    }
}


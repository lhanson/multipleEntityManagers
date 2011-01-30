package example.om;

import javax.persistence.Column;
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
    private final Long id;
    @Column(name = "authorId")
    private Integer authorId;
    @Column(name = "text")
    private String text;

    public EntityA() {
        this.id = -1L;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Long getId() {
        return this.id;
    }
}


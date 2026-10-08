package net.hasagj.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Card extends Element {

    @OneToMany(mappedBy = "card")
    private List<Photo> photos;

    @ManyToOne
    private Group group;
    private Integer orderInGroup;
    /* __coming soon__ */
    /*  custom fields  */
    /* classifications */
}

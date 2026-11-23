package net.hasagj.backend.models;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(
        name = "photo",
        uniqueConstraints = @UniqueConstraint(columnNames = {"collection_id", "path"})
)
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Collection collection;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
    private Integer orderInCard;

    @OneToOne(fetch = FetchType.LAZY)
    private Group group;

    protected Photo() {}

    public Photo(String path, Collection collection) {
        this.path = path;
        this.collection = collection;
    }

    public Long getId() { return this.id; }

    public String getPath() { return this.path; }
    public void setPath(String path) { this.path = path; }

    public Collection getCollection() { return this.collection; }
    public void setCollection(Collection collection) { this.collection = collection; }

    public Card getCard() { return this.card; }
    protected void setCard(Card card) { this.card = card; }

    public Integer getOrderInCard() { return this.orderInCard; }
    protected void setOrderInCard(Integer orderInCard) { this.orderInCard = orderInCard; }

    public Group getGroup() { return this.group; }
    protected void setGroup(Group group) { this.group = group; }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof  HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Photo that = (Photo) o;
        return this.getId() != null && Objects.equals(this.getId(), that.getId());

    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : this.getClass().hashCode();
    }
}

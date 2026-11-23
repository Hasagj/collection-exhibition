package net.hasagj.backend.models;

import jakarta.persistence.*;
import net.hasagj.backend.util.Coordinates;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Collection collection;

    @OneToOne(mappedBy = "element", cascade = CascadeType.ALL, orphanRemoval = true)
    private ElementPlacement placement;

    protected Element() {}

    public Element(String name, String description, Collection collection, Shelf shelf, Coordinates coordinates) {
        this.name = name;
        this.description = description;
        this.collection = collection;
        this.placement = new ElementPlacement(this, shelf, coordinates);
        shelf.addPlacement(placement);
    }

    public Long getId() { return this.id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public Collection getCollection() { return this.collection; }
    protected void setCollection(Collection collection) { this.collection = collection; }

    public ElementPlacement getPlacement() { return this.placement; }
    public void setPlacement(ElementPlacement placement) {
        this.placement = placement;
        placement.setElement(this);
    }

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

        Element that = (Element) o;
        return this.getId() != null && Objects.equals(this.getId(), that.getId());

    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : this.getClass().hashCode();
    }
}

package net.hasagj.backend.models;

import jakarta.persistence.*;
import net.hasagj.backend.util.Coordinates;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
public class ElementPlacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shelf shelf;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private Element element;

    @Embedded
    private Coordinates coordinates;

    protected ElementPlacement() {}

    protected ElementPlacement(Element element, Shelf shelf, Coordinates coordinates) {
        this.element = element;
        this.coordinates = coordinates;
        this.shelf = shelf;
    }

    public Long getId() { return this.id; }

    public Shelf getShelf() { return this.shelf; }
    public void setShelf(Shelf shelf) { this.shelf = shelf; }

    public Element getElement() { return this.element; }
    protected void setElement(Element element) { this.element = element; }

    public Coordinates getCoordinates() { return this.coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

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

        ElementPlacement that = (ElementPlacement) o;
        return this.getId() != null && Objects.equals(this.getId(), that.getId());

    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : this.getClass().hashCode();
    }
}

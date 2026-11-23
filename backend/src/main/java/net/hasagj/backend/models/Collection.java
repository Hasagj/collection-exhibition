package net.hasagj.backend.models;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "root_shelf_id")
    private Shelf rootShelf;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    protected Collection() {}

    public Collection(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() { return this.id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public Shelf getRootShelf() { return rootShelf; }

    public void setRootShelf(Shelf rootShelf) {
        this.rootShelf = rootShelf;
    }

    public LocalDateTime getCreatedAt() { return this.createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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

        Collection that = (Collection) o;
        return this.getId() != null && Objects.equals(this.getId(), that.getId());

    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : this.getClass().hashCode();
    }
}

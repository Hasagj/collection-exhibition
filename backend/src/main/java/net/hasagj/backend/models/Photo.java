package net.hasagj.backend.models;

import jakarta.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    private Long id;

    private String path;

    @ManyToOne
    private Card card;
    private Integer orderInCard;

    @OneToOne
    private Group group;

    public Long getId() { return this.id; }

    public String getPath() { return this.path; }
    public void setPath(String path) { this.path = path; }

    public Card getCard() { return this.card; }
    protected void setCard(Card card) { this.card = card; }

    public Integer getOrderInCard() { return this.orderInCard; }
    protected void setOrderInCard(Integer orderInCard) { this.orderInCard = orderInCard; }

    public Group getGroup() { return this.group; }
    protected void setGroup(Group group) { this.group = group; }
}

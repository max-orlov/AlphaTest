package entities;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    private Long id;
    @Column
    private String color;

    @JoinColumn(name = "CONTAINED_IN")
    private Long contained_in;

    public Item(Long id, String color, Long boxId) {
        this.id = id;
        this.color = color;
        this.contained_in = boxId;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getBox() {
        return contained_in;
    }

    public void setBox(Long box) {
        this.contained_in = box;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", contained_in=" + contained_in +
                '}';
    }
}

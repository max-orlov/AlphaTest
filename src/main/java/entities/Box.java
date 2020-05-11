package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "box")
public class Box {

    @Id
    private Long id;
    @Column
    private Long contained_in;

    public Box(Long id, Long contained_in) {
        this.id = id;
        this.contained_in = contained_in;
    }

    public Box() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContained_in() {
        return contained_in;
    }

    public void setContained_in(Long contained_in) {
        this.contained_in = contained_in;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", contained_in=" + contained_in +
                '}';
    }
}

package modelo;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Tag")
@Where(clause = "deleted = 0")

public class Tag implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name="fromUser")
    private int fromUser;

    @Column(name="toUser")
    private int toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;



    private boolean deleted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }
}

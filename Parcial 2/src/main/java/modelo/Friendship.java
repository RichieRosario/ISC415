package modelo;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "Friendship")
@Table(name = "friendship")
@Where(clause = "deleted = 0")

public class Friendship implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "fechaamistad")
    private Date fechaamistad;

    @Column(name="fromUser")
    private int fromUser;

    @Column(name="toUser")
    private int toUser;

    @Column(name = "isAccepted")
    private Boolean isAccepted;


    private boolean deleted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaamistad() {
        return fechaamistad;
    }

    public void setFechaamistad(Date fechaamistad) {
        this.fechaamistad = fechaamistad;
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

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}

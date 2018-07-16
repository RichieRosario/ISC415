package modelo;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Notification")
@Where(clause = "deleted = 0")

public class Notification implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "notificacion")
    private String notificacion;

    @Column(name = "isSeen")
    private Boolean isSeen;


    private boolean deleted = false;

}

package encapsulacion;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "etiqueta")
@Access(AccessType.FIELD)
@Where(clause = "deleted = 0")

public class Etiqueta implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "etiqueta")
    private String etiqueta;

    private boolean deleted = false;

    public Etiqueta(){

    }
    public Etiqueta( String etiqueta) {

        this.etiqueta = etiqueta;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
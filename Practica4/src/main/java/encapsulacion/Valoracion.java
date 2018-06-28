package encapsulacion;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;


@Entity
@Table(name = "valoracion")
@Access(AccessType.FIELD)
@Where(clause = "deleted = 0")


public class Valoracion implements Serializable {


    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "valoracion")
    private Boolean valoracion;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "comentarioId", nullable = true)
    private Comentario comentarioId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "articuloId", nullable = true)
    private Articulo articuloId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "autorId", nullable = false)
    private Usuario autorId;

    private boolean deleted = false;

    public Long getId() {
        return id;
    }

    public Usuario getAutorid() {
        return autorId;
    }

    public void setAutorid(Usuario autorid) {
        this.autorId = autorid;
    }

    public Articulo getArticuloid() {
        return articuloId;
    }

    public void setArticuloid(Articulo articuloId) {
        this.articuloId = articuloId;
    }


    public Comentario getComentarioId() {
        return comentarioId;
    }

    public void setComentarioId(Comentario comentario) {
        this.comentarioId = comentario;
    }

    public void setValoracion(boolean valoracion){this.valoracion=valoracion;}

    public Boolean getValoracion(){return valoracion;}
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}


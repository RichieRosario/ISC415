package encapsulacion;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;


@Entity
@Table(name = "valoracion")
@Access(AccessType.FIELD)
public class Valoracion implements Serializable {


    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "valoracion")
    private Boolean valoracion;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "comentarioId", nullable = false)
    private Comentario comentarioId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "articuloId", nullable = false)
    private Articulo articuloId;

}

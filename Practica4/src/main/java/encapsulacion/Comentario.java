package encapsulacion;

import dao.Sql2oComentarioDao;
import dao.Sql2oUsuarioDao;
import org.sql2o.Sql2o;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;

@Entity
@Table(name = "comentario")
@Access(AccessType.FIELD)
public class Comentario implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario autorId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "articuloId", nullable = false)
    private Articulo articuloId;


//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "comentarioUsuarios", joinColumns = {@JoinColumn(name = "comentarioId")}, inverseJoinColumns = {@JoinColumn(name = "usuarioId")})
//    private Set<Usuario> listaUsuarios;

    public Comentario(){

        super();
    }
    public Comentario(Long id, String comentario, Usuario autorId, Articulo articuloId) {
        this.id = id;
        this.comentario = comentario;
        this.autorId = autorId;
        this.articuloId = articuloId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

//
    public String getNombreAutor(Long id) {
        Sql2oUsuarioDao sql2oUsuarioDao = new Sql2oUsuarioDao(Usuario.class);
        Usuario usuario = sql2oUsuarioDao.searchById(id);
        return usuario.getNombre();
    }


}

package encapsulacion;

import dao.Sql2oComentarioDao;
import dao.Sql2oUsuarioDao;
import org.hibernate.annotations.Where;
import org.sql2o.Sql2o;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;

@Entity
@Table(name = "comentario")
@Access(AccessType.FIELD)
@Where(clause = "deleted = 0")

public class Comentario implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "comentario")
    private String comentario;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "articuloId", nullable = false)
    private Articulo articuloId;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario autorId;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "comentarioValoraciones", joinColumns = {@JoinColumn(name = "comentarioId")}, inverseJoinColumns = {@JoinColumn(name = "valoracionId")})
    private Set<Valoracion> valoraciones;

    private boolean deleted = false;

public Comentario(){

    valoraciones = new HashSet<>();
}


    public Comentario(Long id, String comentario, Usuario autorId, Articulo articuloId,  Set<Valoracion> valoraciones) {
        this.id = id;
        this.comentario = comentario;
        this.autorId = autorId;
        this.articuloId = articuloId;
        this.valoraciones = valoraciones;
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
    public Set<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(Set<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public Integer getLikes(){
        int ans=0;
        for(Valoracion val : valoraciones){
            if(val.getValoracion())ans++;
        }
        return ans;
    }


    public Integer getDislikes(){
        int ans=0;
        for(Valoracion val : valoraciones){
            if(!val.getValoracion())ans++;
        }
        return ans;
    }
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

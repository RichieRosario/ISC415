package encapsulacion;

import dao.Sql2oArticuloDao;
import org.hibernate.annotations.Where;

import org.sql2o.Sql2o;

import javax.enterprise.inject.TransientReference;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "articulo")
@Access(AccessType.FIELD)
@Where(clause = "deleted = 0")

public class Articulo implements Serializable {

    @Id
    @GeneratedValue()
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "cuerpo", nullable = false)
    private String cuerpo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "autorId")
    private Usuario autorId;

    @Column(name = "fecha", nullable = false)
    private Date fecha;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "articuloComentarios", joinColumns = {@JoinColumn(name = "articuloId")}, inverseJoinColumns = {@JoinColumn(name = "comentarioId")})
    private Set<Comentario> comentarios;

    @JoinTable(name = "articuloEtiquetas", joinColumns = {@JoinColumn(name = "articuloId")}, inverseJoinColumns = {@JoinColumn(name = "etiquetaId")})
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Etiqueta> etiquetas;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "articuloValoraciones", joinColumns = {@JoinColumn(name = "articuloId")}, inverseJoinColumns = {@JoinColumn(name = "valoracionId")})
    private Set<Valoracion> valoraciones;

    private boolean deleted = false;

    private String resumen;

    private Long idusuario;

    public Articulo() {
        comentarios = new HashSet<>();
        etiquetas = new HashSet<>();
        valoraciones = new HashSet<>();
    }

        public Articulo(Long id, String titulo, String cuerpo, Usuario autor, Date fecha,Set<Comentario> comentarios, Set<Etiqueta> etiquetas,
                    Set<Valoracion> valoraciones) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autorId = autor;
        this.fecha = fecha;
        this.comentarios = comentarios;
        this.etiquetas = etiquetas;
        this.valoraciones = valoraciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutorId() {
        return autorId;
    }

    public void setAutor(Usuario autor) {
        this.autorId = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Set<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(Set<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    @Transient
    public String getNombreAutor() {

        Sql2oArticuloDao sql2oArticuloDao = new Sql2oArticuloDao(Articulo.class);

        return sql2oArticuloDao.searchById(this.autorId.getId()).getNombre();
    }

    public String getResumen(){

        String fini="";

       for (int i=0;i<Math.min(this.cuerpo.length(),70);i++){
            fini+=this.cuerpo.charAt(i);
        }
        return fini;
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
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
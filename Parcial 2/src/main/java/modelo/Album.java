package modelo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "Album")
@Table(name = "album")
@Where(clause = "deleted = 0")

public class Album implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fechacreacion")
    private Date fechacreacion;

    @Column(name = "descripcion")
    private String nombredescripcion;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id",nullable = true, updatable = false)
    private User user;


    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Photo> photos;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name ="tag_id")
    private Tag tag;

    private boolean deleted = false;

    public Album(){super();}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getNombredescripcion() {
        return nombredescripcion;
    }

    public void setNombredescripcion(String nombredescripcion) {
        this.nombredescripcion = nombredescripcion;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Photo getCover() {
        if(photos.size()>0) return photos.get(0);
        else return null;
    }

    public User getUser(){ return user;}

    public Tag getEtiqueta() {
        return tag;
    }

    public void setEtiqueta(Tag etiqueta) {
        this.tag = etiqueta;
    }

    public void setUser(User user){  this.user=user;}

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}

package modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.*;

@Entity(name = "Post")
@Table(name = "post")
@Where(clause = "deleted = 0")

public class Post implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "texto")
    private String texto;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "likes")
    private int likes;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "postValoraciones", joinColumns = {@JoinColumn(name = "post_id")}, inverseJoinColumns = {@JoinColumn(name = "likeDislike_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<LikeDislike> valoraciones;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "wall_id", nullable = true, updatable = false)
    private Wall wall;

    @OneToMany(  mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Comment> comments = new HashSet<>();;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name ="tag_id")
    private Tag tag;

    private boolean deleted = false;

    public Post(){
        super();
    }
    public Post(Integer id, String texto, LocalDate fecha, Integer likes, Set<LikeDislike> valoraciones,
                User user, Wall wall, Set<Comment> comments, Tag tag,Photo photo)
    {
        this.id = id;
        this.texto = texto;
        this.fecha = fecha;
        this.likes = likes;
        this.valoraciones = valoraciones;
        this.user = user;
        this.wall = wall;
        this.comments = comments;
        this.tag = tag;
        this.photo = photo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {

            this.comments = comments;

    }
    public Photo getPhoto() {
    return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Tag getEtiqueta() {
        return tag;
    }

    public void setEtiqueta(Tag etiqueta) {
        this.tag = etiqueta;
    }

    public Set<LikeDislike> getValoraciones() {
        return valoraciones;
    }

    public int getcantlikes(){
        int conta=0;
        for(LikeDislike val : valoraciones){
                if(val.getValoracion())conta++;
        }
        return conta;
    }

    public int getcantdislikes(){
        int conta=0;
        for(LikeDislike val : valoraciones){
            if(!val.getValoracion())conta++;
        }
        return conta;
    }

    public void setValoraciones(Set<LikeDislike> valoraciones) {
        this.valoraciones = valoraciones;
    }
}

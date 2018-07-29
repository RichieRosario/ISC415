package modelo;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Loader;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "Photo")
@Table(name = "photo")
@Where(clause = "deleted = 0")

public class Photo implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "caption")
    private String caption;

    @Loader
    @Column(name = "foto",columnDefinition = "BLOB")
    private byte[] foto;

    @OneToOne( mappedBy = "photo", fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Post post;

    @OneToMany( mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Comment> comments = new HashSet<>();

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name ="tag_id")
    private Tag tag;


    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "album_id",nullable = true, updatable = false)
    private Album album;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "photoValoraciones", joinColumns = {@JoinColumn(name = "photo_id")}, inverseJoinColumns = {@JoinColumn(name = "likeDislike_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<LikeDislike> valoraciones;


    private boolean deleted = false;


    public Photo() {super();}
    public Photo(Integer id, String caption, Set<LikeDislike> valoraciones,Set<Comment> comments, Tag tag, Album album, Post post){
        this.id = id;
        this.caption = caption;
        this.post = post;
        this.valoraciones = valoraciones;
        this.album = album;
        this.comments = comments;
        this.tag = tag;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Tag getEtiqueta() {
        return tag;
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

    public Set<LikeDislike> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(Set<LikeDislike> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public void setEtiqueta(Tag etiqueta) {
        this.tag = etiqueta;
    }

    public Album getAlbums() {
        return album;
    }

    public void setAlbums(Album albums) {
        this.album = albums;
    }

    public String getFoto() {
        return Base64.getEncoder().encodeToString(foto);
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}

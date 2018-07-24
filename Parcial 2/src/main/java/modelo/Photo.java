package modelo;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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

    @Column(name = "foto",columnDefinition = "BLOB")
    private byte[] foto;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "photo")
    private Post post;

    @OneToMany( mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "photo_tag",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Tag> etiquetas = new HashSet<>();

    @ManyToMany(mappedBy = "photos")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Album> albums = new HashSet<>();

    @OneToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "photoValoraciones", joinColumns = {@JoinColumn(name = "photo_id")}, inverseJoinColumns = {@JoinColumn(name = "likeDislike_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<LikeDislike> valoraciones;


    private boolean deleted = false;


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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Set<Tag> getEtiquetas() {
        return etiquetas;
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

    public void setEtiquetas(Set<Tag> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}

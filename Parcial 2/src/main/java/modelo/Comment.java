package modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;
import java.util.Optional;
import java.util.Set;

@Entity(name = "Comment")
@Table(name = "comment")
@Where(clause = "deleted = 0")

public class Comment implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "likes")
    private int likes;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "commentValoraciones", joinColumns = {@JoinColumn(name = "comment_id",  nullable = true)}, inverseJoinColumns = {@JoinColumn(name = "likeDislike_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<LikeDislike> valoraciones;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "post_id",nullable = true, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "event_id",nullable = true, updatable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "photo_id",nullable = true, updatable = false)
    private Photo photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean deleted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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
}

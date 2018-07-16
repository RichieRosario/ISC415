package modelo;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Entity(name = "LikeDislike")
@Table(name = "likedislike")
@Where(clause = "deleted = 0")

public class LikeDislike implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "valoracion")
    private Boolean valoracion;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Photo photo;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Comment comment;

    private boolean deleted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getValoracion() {
        return valoracion;
    }

    public void setValoracion(Boolean valoracion) {
        this.valoracion = valoracion;
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}

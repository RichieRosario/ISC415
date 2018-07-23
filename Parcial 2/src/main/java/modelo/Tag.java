package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "Tag")
@Table(name = "tag")
@Where(clause = "deleted = 0")

public class Tag implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name="fromUser")
    private User fromUser;

    @Column(name="toUser")
    private User toUser;

    @ManyToMany(mappedBy = "etiquetas")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(mappedBy = "etiquetas")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Photo> photos = new HashSet<>();



    private boolean deleted = false;

    public Tag(){
        super();
    }
    public Tag(User toUser) {
        this.toUser = toUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}

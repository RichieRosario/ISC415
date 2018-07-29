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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = true, updatable = false)
    private User user;



    @OneToOne(mappedBy = "tag", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Post post;


    @OneToOne(mappedBy = "tag", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Photo photo;

    @OneToOne(mappedBy = "tag", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Album album;




    private boolean deleted = false;

    public Tag(){
        super();
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

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPosts(Post post) {
        this.post = post;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }


    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }


}

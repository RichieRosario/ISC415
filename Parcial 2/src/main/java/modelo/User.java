package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dao.FriendshipDaoImpl;
import dao.UserDaoImpl;
import hibernate.HibernateUtil;
import javafx.geometry.Pos;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "User")
@Table(name = "user")
@Where(clause = "deleted = 0")

public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @NotEmpty(message="El usuario no puede estar vacio")
    @Column(name = "username")
    private String username;

    @NotEmpty(message="La contrasena no puede estar vacia")
    @Column(name = "password")
    private String password;

    @NotEmpty(message="El correo no puede estar vacio")
    @Column(name = "email")
    private String email;

    @Column(name = "administrator")
    private boolean administrator;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private boolean deleted = false;

    public User(){

        super();
    }
    public User(Integer id, String username, String password, String email, Boolean administrator, List<Post> posts, List<Event> events, List<Comment> comments)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.administrator = administrator;
        this.posts = posts;
        this.events = events;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> usersMayKnow(int userId){

        FriendshipDaoImpl friendshipDao = null;
        UserDaoImpl userDao = null;

        List<Integer> amigos = friendshipDao.getAllFriends(userDao.findOne(userId));
        List<Integer> noamigos = null;
        List<User> todos = userDao.getAll();

        for (User usuario : todos) {
            for (Integer amigo : amigos) {
                if (usuario.getId() == amigo) {
                    continue;
                } else noamigos.add(usuario.getId());
            }
        }
        List<User> desconocidos = null;

        for (Integer noamigo : noamigos) {
            desconocidos.add(userDao.findOne(noamigo));
        }

        List<User> posiblesConocidos = null;

        for (User desconocido : desconocidos) {
            if(userDao.getProfile(desconocido.getId()).getCiudadactual() == userDao.getProfile(userDao.findOne(userId).getId()).getCiudadactual()
                    || userDao.getProfile(desconocido.getId()).getLugarestudio() == userDao.getProfile(userDao.findOne(userId).getId()).getLugarestudio()
                    || userDao.getProfile(desconocido.getId()).getLugartrabajo() == userDao.getProfile(userDao.findOne(userId).getId()).getLugartrabajo()){

                posiblesConocidos.add(desconocido);
            }
        }
        return posiblesConocidos;
    }
}

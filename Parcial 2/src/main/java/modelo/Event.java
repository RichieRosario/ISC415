package modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Event")
@Table(name = "event")
@Where(clause = "deleted = 0")

public class Event implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "evento")
    private String evento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(  mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "eventValoraciones", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "likeDislike_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<LikeDislike> valoraciones;



    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "wall_id", nullable = true, updatable = false)
    private Wall wall;

    private boolean deleted = false;

    public int getId() {
        return id;
    }

    public Set<Comment> getComments(){
        return comments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}

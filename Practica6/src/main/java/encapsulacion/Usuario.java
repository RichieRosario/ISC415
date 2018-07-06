package encapsulacion;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "usuario")
@Access(AccessType.FIELD)
@Where(clause = "deleted = 0")

public class Usuario implements Serializable{


    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "password")
    private String password;

    @Column(name = "administrator")
    private boolean administrator;

    @Column(name = "autor")
    private boolean autor;

    private boolean deleted = false;


    public Usuario(){

        super();
    }



    public Usuario(Long id, String username, String nombre, String password, Boolean administrator, Boolean autor) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.administrator = administrator;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public Boolean isAutor() {
        return autor;
    }

    public void setAutor(Boolean autor) {
        this.autor = autor;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

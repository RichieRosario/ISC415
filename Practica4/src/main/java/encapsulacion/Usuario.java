package encapsulacion;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

public class Usuario implements Serializable{



    private Long id;
    private String username;
    private String nombre;
    private String password;
    private boolean administrator;
    private boolean autor;

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






}

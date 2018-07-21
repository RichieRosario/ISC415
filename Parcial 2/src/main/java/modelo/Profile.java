package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.Loader;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "Profile")
@Table(name = "profile")
@Where(clause = "deleted = 0")

public class Profile implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Loader
    @Column(name = "profilepic", columnDefinition = "BLOB")
    private byte[] profilepic;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fechanacimiento")
    private Date fechanacimiento;

    @Column(name = "lugarnacimiento")
    private String lugarnacimiento;

    @Column(name = "ciudadactual")
    private String ciudadactual;

    @Column(name = "lugarestudio")
    private String lugarestudio;

    @Column(name = "lugartrabajo")
    private String lugartrabajo;

    @Column(name = "sexo")
    private Character sexo;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    private boolean deleted = false;

    public Profile(){
        super();
    }
    public Profile(int id, String nombre, String apellido, Date fechanacimiento, String lugarnacimiento,
                   String ciudadactual, String lugarestudio, String lugartrabajo, Character sexo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechanacimiento = fechanacimiento;
        this.lugarnacimiento = lugarnacimiento;
        this.ciudadactual = ciudadactual;
        this.lugarestudio = lugarestudio;
        this.lugartrabajo = lugartrabajo;
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getLugarnacimiento() {
        return lugarnacimiento;
    }

    public void setLugarnacimiento(String lugarnacimiento) {
        this.lugarnacimiento = lugarnacimiento;
    }

    public String getCiudadactual() {
        return ciudadactual;
    }

    public void setCiudadactual(String ciudadactual) {
        this.ciudadactual = ciudadactual;
    }

    public String getLugarestudio() {
        return lugarestudio;
    }

    public void setLugarestudio(String lugarestudio) {
        this.lugarestudio = lugarestudio;
    }

    public String getLugartrabajo() {
        return lugartrabajo;
    }

    public void setLugartrabajo(String lugartrabajo) {
        this.lugartrabajo = lugartrabajo;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(byte[] profilepic) {
        this.profilepic = profilepic;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
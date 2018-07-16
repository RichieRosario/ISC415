package modelo;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "Profile")
@Table(name = "profile")
@Where(clause = "deleted = 0")

public class Profile implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @NotEmpty(message="El nombre no puede estar vacio")
    @Column(name = "nombre")
    private String nombre;

    @NotEmpty(message="El apellido no puede estar vacio")
    @Column(name = "apellido")
    private String apellido;

    @NotEmpty(message="La fecha de nacimiento no puede estar vacia")
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

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    private boolean deleted = false;

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}

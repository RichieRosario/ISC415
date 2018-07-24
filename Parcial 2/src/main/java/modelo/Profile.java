package modelo;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Base64;
import java.util.Date;
import javax.persistence.*;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.h2.util.DateTimeUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Loader;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Where;

@Entity(name = "Profile")
@Table(name = "profile")
@Where(clause = "deleted = 0")

public class Profile implements Serializable {

    @Id
    @GeneratedValue
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
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

    public String getFechanacimiento() {
        String fini="";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        fini+= format.format(fechanacimiento);

        return fini;
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

    public String getSexo() {
        String temp="";
        if(sexo=='M'){
            temp="Masculino";
        }
        else{
            temp="Femenino";
        }

        return temp;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public User getUser() {
        return user;
    }

    public int getUserId(){
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProfilepic() {


        return Base64.getEncoder().encodeToString(profilepic);
    }

    public void setProfilepic(byte[] profilepic) {
        this.profilepic = profilepic;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public int getEdad(){

        LocalDate then = LocalDate.ofYearDay(fechanacimiento.getYear(),fechanacimiento.getDay());
        LocalDate now = LocalDate.now();
        return Period.between(then,now).getYears();
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
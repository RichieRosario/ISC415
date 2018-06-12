package encapsulacion;

import dao.Sql2oArticuloDao;
import org.sql2o.Sql2o;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Articulo implements Serializable {

    private Long id;
    private String titulo;
    private String cuerpo;
    private Long autorId;
    private Date fecha;
    private List<Comentario> comentarios = new ArrayList<>();
    private List<Etiqueta> etiquetas = new ArrayList<>();
    private Long idusuario;


    public Articulo(Long id, String titulo, String cuerpo,
                    Long autor, Date fecha, List<Comentario> comentarios,
                    List<Etiqueta> etiquetas) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autorId = autor;
        this.fecha = fecha;
        this.comentarios = comentarios;
        this.etiquetas = etiquetas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutor(Long autor) {
        this.autorId = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getNombreAutor() {
        Sql2oArticuloDao sql2oArticuloDao = new Sql2oArticuloDao(new Sql2o("jdbc:h2:~/blog", "", ""));

        return sql2oArticuloDao.searchById(this.autorId).getNombre();
    }
    public String getResumen(){

        String fini="";
        for(int i=0;i<70;i++){
            fini+= this.getCuerpo().charAt(i);
        }
        return fini;
    }

}
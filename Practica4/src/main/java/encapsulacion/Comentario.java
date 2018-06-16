package encapsulacion;

import dao.Sql2oComentarioDao;
import dao.Sql2oUsuarioDao;
import org.sql2o.Sql2o;

import java.io.Serializable;

public class Comentario implements Serializable{


    private Long id;
    private String comentario;
    private Long autorId;
    private Long articuloId;

    public Comentario(){

        super();
    }
    public Comentario(Long id, String comentario, Long autorId, Long articuloId) {
        this.id = id;
        this.comentario = comentario;
        this.autorId = autorId;
        this.articuloId = articuloId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getAutorid() {
        return autorId;
    }

    public void setAutorid(Long autorid) {
        this.autorId = autorid;
    }

    public Long getArticuloid() {
        return articuloId;
    }

    public void setArticuloid(Long articuloId) {
        this.articuloId = articuloId;
    }


    public String getNombreAutor(Long id) {
        Sql2oComentarioDao sql2oComentarioDao = new Sql2oComentarioDao(new Sql2o("jdbc:h2:~/blog", "", ""));
        Sql2oUsuarioDao sql2oUsuarioDao = new Sql2oUsuarioDao(new Sql2o("jdbc:h2:~/blog", "", ""));
        Usuario usuario = sql2oUsuarioDao.searchById(id);
        return usuario.getNombre();
    }


}

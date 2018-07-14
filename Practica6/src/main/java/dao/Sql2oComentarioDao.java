package dao;


import encapsulacion.Comentario;
import encapsulacion.Usuario;
import org.hibernate.Session;
import org.sql2o.Connection;
import java.util.List;

public class Sql2oComentarioDao extends Repositorio<Comentario, Long> implements ComentarioDao {


    public Sql2oComentarioDao(Class<Comentario> comentarioClass) {
        super(comentarioClass);
    }

    @Override
    public void add(Comentario comentario){

        super.add(comentario);

    }

    @Override
    public Comentario findOne(Long id) {

        return super.findOne(id);
    }

    @Override
    public List<Comentario> getAll() {

        return super.getAll();
    }


    @Override
    public void update(Comentario comentario) {

        super.update(comentario);
    }

    @Override
    public void deleteById(Comentario comentario) {

        comentario.setDeleted(true);
        this.update(comentario);    }


}

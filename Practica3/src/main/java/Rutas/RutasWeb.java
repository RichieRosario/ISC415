package Rutas;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import dao.Sql2oArticuloDao;
import dao.Sql2oComentarioDao;
import dao.Sql2oEtiquetaDao;
import dao.Sql2oUsuarioDao;
import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Usuario;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.*;

public class RutasWeb {

    public RutasWeb(final Sql2oArticuloDao sql2oArticuloDao, final FreeMarkerEngine freeMarkerEngine) {

        Sql2oUsuarioDao usuarioDao;
        Sql2oArticuloDao articuloDao;
        Sql2oComentarioDao comentarioDao;
        Sql2oEtiquetaDao etiquetaDao;
        Connection conn;
        Gson gson = new Gson();

        Sql2o sql2o = new Sql2o( "jdbc:h2:~/Practica3", "sa", "");

        usuarioDao = new Sql2oUsuarioDao(sql2o);
        articuloDao = new Sql2oArticuloDao(sql2o);
        comentarioDao = new Sql2oComentarioDao(sql2o);
        etiquetaDao = new Sql2oEtiquetaDao(sql2o);

        conn = sql2o.open();

        get("/inicio", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            List<Articulo> listaArticulos = articuloDao.getAll();

            attributes.put("articulos", listaArticulos);

            return new ModelAndView(attributes, "inicio.ftl");
        }, freeMarkerEngine);

        get("articulo", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap queryParamsMap = request.queryMap();

            Long id = Long.parseLong(queryParamsMap.get("id").value());

            Articulo articulo = articuloDao.findOne(id);
            List<Comentario> listaComentarios = articulo.getComentarios();

            attributes.put("articulos", articulo);
            attributes.put("comentarios", listaComentarios);

            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);

        get("/usuario/nuevo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "formularioUsuario.ftl");
        }, freeMarkerEngine);

        post("usuario/nuevo", (request, response) -> {

            QueryParamsMap map = request.queryMap();

            Usuario usuario = new Usuario();

            usuario.setUsername(map.get("username").value());
            usuario.setNombre(map.get("nombre").value());
            usuario.setPassword(map.get("password").value());
            usuario.setAdministrator(Boolean.parseBoolean(map.get("administrator").value()));
            usuario.setAutor(Boolean.parseBoolean(map.get("autor").value()));

           if(usuarioDao.searchByUsername(usuario.getUsername())==null){

               usuarioDao.add(usuario);

               response.redirect("/login");

               return null;
           }
          else {
            return "Usuario ya existe!";
        }

        });

        get("login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "formularioLogin.ftl");
        }, freeMarkerEngine);

        post("login", (request, response) -> {

            QueryParamsMap map = request.queryMap();

            Usuario usuario = usuarioDao.searchByUsername(map.get("usuario").value());

            if(usuario != null){

                if(map.get("password").value().equals(usuario.getPassword())){
                    response.redirect("/inicio");

                    return null;
                }
            }

            return "Usuario o contrasena incorrecto";
        });

    }
}

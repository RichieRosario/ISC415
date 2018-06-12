package Rutas;


import com.google.gson.Gson;
import dao.Sql2oArticuloDao;
import dao.Sql2oComentarioDao;
import dao.Sql2oEtiquetaDao;
import dao.Sql2oUsuarioDao;
import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.Cookie;


import static spark.Spark.*;

public class RutasWeb {

    public RutasWeb(final FreeMarkerEngine freeMarkerEngine) {

        Sql2oUsuarioDao usuarioDao;
        Sql2oArticuloDao articuloDao;
        Sql2oComentarioDao comentarioDao;
        Sql2oEtiquetaDao etiquetaDao;
        Connection conn;
        Gson gson = new Gson();

        Sql2o sql2o = new Sql2o( "jdbc:h2:~/blog", "", "");

        usuarioDao = new Sql2oUsuarioDao(sql2o);
        articuloDao = new Sql2oArticuloDao(sql2o);
        comentarioDao = new Sql2oComentarioDao(sql2o);
        etiquetaDao = new Sql2oEtiquetaDao(sql2o);

        conn = sql2o.open();

//       Usuario usuarioPorDefecto = new Usuario(1L, "admin", null, "admin", true, true);
//        usuarioDao.add(usuarioPorDefecto);
//
//        Articulo articuloPrueba = new Articulo(1L, "Lebron James", "Simplemente el mejor del mundo, sin discusion.", usuarioPorDefecto.getId(),  new Date(new java.util.Date().getTime()), null, null);
//                articuloDao.add(articuloPrueba);


        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            List<Articulo> articulos = articuloDao.getAll();


            attributes.put("articulos", articulos);

            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);






        get("articulo", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap queryParamsMap = request.queryMap();

            Long id = Long.parseLong(queryParamsMap.get("id").value());

            Articulo articulo = articuloDao.findOne(id);
            List<Comentario> listaComentarios = articulo.getComentarios();

            attributes.put("articulos", articulo);
            attributes.put("comentarios", listaComentarios);

            return new ModelAndView(attributes, "post.ftl");
        }, freeMarkerEngine);


        get("/articulo/nuevo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "formulario.ftl");
        }, freeMarkerEngine);

        post("/articulo/nuevo", (request, response) -> {

            Long idarticulo = Long.parseLong(request.params("id"));
            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            String etiquetas = request.queryParams("etiquetas");

            List<Etiqueta> etiq = new ArrayList<Etiqueta>();

            for (String eti : etiquetas.split(",")) {
                etiq.add(new Etiqueta(0L, eti));
            }

            Articulo articulo = new Articulo(idarticulo, titulo, cuerpo, null, null, null, etiq);
            articuloDao.add(articulo);


            response.redirect("/");

            return null;
        });




        get("/articulos/editar/:id", (request, response) -> {

            Long idarticulo = Long.parseLong(request.params("id"));

            Articulo articulo = articuloDao.findOne(idarticulo);

            Map<String, Object> attributes = new HashMap<>();

            attributes.put("articulos", articulo);

            return new ModelAndView(attributes, "modificar.ftl");
        }, freeMarkerEngine);

        post("/articulos/editar/:id", (request, response) -> {

            Long idarticulo = Long.parseLong(request.params("id"));
            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            String etiquetas = request.queryParams("etiquetas");

            List<Etiqueta> etiq = new ArrayList<Etiqueta>();

            for (String eti : etiquetas.split(",")) {
                etiq.add(new Etiqueta(0L, eti));
            }

            Articulo articulo = new Articulo(idarticulo, titulo, cuerpo, null, null, null, etiq);
            articuloDao.update(articulo);

            response.redirect("/articulos");

            return null;
        });
        get("/articulos/borrar/:id", (request, response) -> {

            Long idarticulo = Long.parseLong(request.params("id"));

            Articulo articulo = articuloDao.findOne(idarticulo);

            if (articulo != null){
                articuloDao.deleteById(idarticulo);
            }

            response.redirect("/articulos");

            return null;

        },freeMarkerEngine);




        get("/usuarios", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("usuarios", usuarioDao.getAll());

            return new ModelAndView(attributes, "usuarios.ftl");
        }, freeMarkerEngine);


        get("/usuarios/nuevo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "formularioUsuario.ftl");
        }, freeMarkerEngine);


        post("usuarios/nuevo", (request, response) -> {

            QueryParamsMap map = request.queryMap();

            Usuario usuario = new Usuario();
            usuario.setUsername(map.get("username").value());
            usuario.setNombre(map.get("nombre").value());
            usuario.setPassword(map.get("password").value());
            usuario.setAdministrator(Boolean.parseBoolean(map.get("administrator").value()));
            usuario.setAutor(Boolean.parseBoolean(map.get("autor").value()));

            Sql2oUsuarioDao usuarioDao1 = new Sql2oUsuarioDao(sql2o);
           if(usuarioDao1.searchByUsername(usuario.getUsername())==null){

               usuarioDao1.add(usuario);

               response.redirect("/login");

               return null;
           }
          else {
            return "Usuario ya existe!";
        }

        });

        get("/usuarios/editar/:id", (request, response) -> {

            Long idusuario = Long.parseLong(request.params("id"));

            Usuario usuario = usuarioDao.findOne(idusuario);

            Map<String, Object> attributes = new HashMap<>();

            attributes.put("usuarios", usuario);

            return new ModelAndView(attributes, "editarUsuario.ftl");
        }, freeMarkerEngine);

        post("/usuarios/editar/:id", (request, response) -> {

            Long idusuario = Long.parseLong(request.params("id"));
            String username = request.queryParams("username");
            String nombre = request.queryParams("nombre");
            String password = request.queryParams("password");
            Boolean administrator = Boolean.parseBoolean(request.queryParams("administrator"));
            Boolean autor = Boolean.parseBoolean(request.queryParams("autor"));



            Usuario usuario = new Usuario(idusuario, username, nombre, password, administrator, autor);
            usuarioDao.update(usuario);

            response.redirect("/usuarios");

            return null;
        });

        get("/usuarios/borrar/:id", (request, response) -> {

            Long idusuario = Long.parseLong(request.params("id"));

            Usuario usuario = usuarioDao.findOne(idusuario);

            if (usuario != null){
                usuarioDao.deleteById(idusuario);
            }

            response.redirect("/usuarios");

            return null;

        },freeMarkerEngine);






        get("login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "formularioLogin.ftl");
        }, freeMarkerEngine);


        post("login", (request, response) -> {

            QueryParamsMap map = request.queryMap();

            Usuario usuario = usuarioDao.searchByUsername(map.get("username").value());
//
//            if(aqui va la logica del checkbox){
//
//                Session session = request.session();
//                session.attribute("username", map.get("username").value());
//                //setting session to expiry in 30 mins
//                session.maxInactiveInterval(604800);
//                response.cookie("username", map.get("username").value(), 604800);
//            }


            if (map.get("username").value() == null || map.get("username").value().isEmpty()) {
                return "Digite un nombre de usuario";
            }

            if (map.get("password").value() == null || map.get("password").value().isEmpty()) {
                return "Digite una contrasena";
            }

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

package Rutas;


import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.xs.ShortList;
import com.sun.org.apache.xpath.internal.operations.Bool;
import dao.*;
import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import org.jasypt.util.text.StrongTextEncryptor;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import servicios.Hash;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;
import sun.nio.cs.US_ASCII;


import java.sql.Date;
import java.util.*;


import javax.servlet.http.Cookie;


import static spark.Spark.*;

public class RutasWeb {

    public RutasWeb(final FreeMarkerEngine freeMarkerEngine) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword("blog");
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
            boolean autenticado = false;
            QueryParamsMap map2 = request.queryMap();
            boolean admin=false;
            boolean autor=false;
            Usuario user = new Usuario();


            if(request.cookie("username")!=null){
                autenticado=true;
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
                autor = user.isAutor();
                admin=user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
                autenticado=true;
                autor = user.isAutor();
                admin=user.isAdministrator();

            }



            if(autenticado == true) {


                autor=user.isAutor();
                admin=user.isAdministrator();

                List<Articulo> articulos = articuloDao.getAll();
                attributes.put("autor", autor);
                attributes.put("usuariodentro",user.getNombre());
                attributes.put("admin", admin);
                attributes.put("autenticado", autenticado);
                attributes.put("articulos", articulos);}



            else {
                List<Articulo> articulos = articuloDao.getAll();
                attributes.put("usuariodentro", "Huesped");
                attributes.put("autor", false);
                attributes.put("admin", false);
                attributes.put("autenticado", false);
                attributes.put("articulos", articulos);
            }


            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);


        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            boolean autenticado = false;
            boolean admin=false;
            boolean autor=false;
            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }





            if(autenticado == true) {

                    response.redirect("/");}


            else {
                attributes.put("autor", false);
                attributes.put("admin", false);
                attributes.put("autenticado", false);
            }


            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);




        post("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String username = request.queryParams("username") != null ? request.queryParams("username") : "";
            String password = request.queryParams("password") != null ? request.queryParams("password") : "";
            String remember = request.queryParams("remember") != null ? request.queryParams("remember") : "";
            Usuario user = usuarioDao.searchByUsername(username);
            if( user.getUsername().equals(username) && user.getPassword().equals(Hash.sha1(password))){
                QueryParamsMap map2 = request.queryMap();
                Session session = request.session();
                session.attribute("username", map2.get("username").value());
                session.maxInactiveInterval(900);

                if(remember.equals("remember")){
                    response.cookie("username",textEncryptor.encrypt(map2.get("username").value()), 604800);
                    request.cookie("username");

                }

                List<Articulo> articulos = articuloDao.getAll();
                attributes.put("autor", user.isAutor());
                attributes.put("usuariodentro",user.getNombre());
                attributes.put("admin", user.isAdministrator());
                attributes.put("autenticado", true);
                attributes.put("articulos", articulos);
                response.redirect("/");
            }

            else{
                List<Articulo> articulos = articuloDao.getAll();
                attributes.put("usuariodentro","Huesped");
                attributes.put("autor", false);
                attributes.put("admin", false);
                attributes.put("autenticado", false);
                attributes.put("articulos", articulos);}



            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);

        get("/logout", (request,response) ->{
            Session session = request.session();
            session.removeAttribute("username");
            response.removeCookie("username");
            response.redirect("/");

            Map<String, Object> attributes = new HashMap<>();

                List<Articulo> articulos = articuloDao.getAll();
            attributes.put("usuariodentro","Huesped");
                attributes.put("autor", false);
                attributes.put("admin", false);
                attributes.put("autenticado", false);
                attributes.put("articulos", articulos);

        return new ModelAndView(attributes, "index.ftl");
        },freeMarkerEngine);

        get("/articulo/:id", (request, response) -> {
            boolean autenticado = false;
            boolean autor = false;
            boolean admin = false;
            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }


            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap queryParamsMap = request.queryMap();


            Long id = Long.parseLong(request.params("id"));


            Articulo articulo = articuloDao.findOne(id);

            List<Comentario> listaComentarios = articuloDao.obtenerComentarios(id);
            List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();

            List<Long> etiquetasids = articuloDao.obtenerEtiquetas(id);

            for(Long ideti : etiquetasids){
                    etiquetas.add(etiquetaDao.findOne(ideti));
            }
            int cantidadcomentarios = listaComentarios.size();

            attributes.put("cantidadcomentarios", cantidadcomentarios);
            attributes.put("admin", admin);
            attributes.put("autor", autor);
            attributes.put("autenticado", autenticado);
            attributes.put("articulo", articulo);
            attributes.put("idarticulo", id.toString());
            attributes.put("listaComentarios", listaComentarios);
            attributes.put("etiquetas", etiquetas);

            return new ModelAndView(attributes, "post.ftl");

        }, freeMarkerEngine);








        post("/articulo/:id", (request, response) -> {
            boolean autenticado=false;
            QueryParamsMap map = request.queryMap();
            Long id = Long.parseLong(request.params("id"));
            System.out.println(request.params("id"));
            Usuario usuario = new Usuario();
            if(request.cookie("username")!=null)
            {autenticado=true;
            usuario = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));


            }
            else if(usuarioDao.searchByUsername(request.session().attribute("username"))!=null && request.cookie("username")==null){
            autenticado=true;
                usuario = usuarioDao.searchByUsername(request.session().attribute("username"));}

            Comentario comentario = new Comentario();
            comentario.setAutorid(usuario.getId());
            comentario.setComentario(map.get("commentbody").value());

            Sql2oArticuloDao sql2oArticuloDao = new Sql2oArticuloDao(sql2o);
            Articulo articulo = new Articulo();
            articulo = sql2oArticuloDao.findOne(id);
            comentario.setArticuloid(articulo.getId());
            comentarioDao.add(comentario);
            response.redirect("/articulo/" + id.toString());

            return "Ok";
        });

//
               get("/articulos/nuevo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
                   boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
                    boolean admin=false;
                    boolean autor=false;


                   Usuario usuario = new Usuario();
                   Sql2oUsuarioDao sql2oUsuarioDao = new Sql2oUsuarioDao(sql2o);
                   if(request.cookie("username")!=null)
                   {autenticado=true;
                       usuario = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));


                   }
                   else if(sql2oUsuarioDao.searchByUsername(request.session().attribute("username"))!=null && request.cookie("username")==null){
                       usuario = sql2oUsuarioDao.searchByUsername(request.session().attribute("username"));}






                   attributes.put("autor", autor);
            attributes.put("admin", admin);
            attributes.put("autenticado", autenticado);

            return new ModelAndView(attributes, "crearArticulo.ftl");
        }, freeMarkerEngine);

        post("/articulos/nuevo", (request, response) -> {

            QueryParamsMap map = request.queryMap();

            Long id;

            Articulo articulo = new Articulo();
            articulo.setTitulo(map.get("titulo").value());
            articulo.setCuerpo(map.get("cuerpo").value());

            Usuario usuario = new Usuario();
            Sql2oUsuarioDao sql2oUsuarioDao = new Sql2oUsuarioDao(sql2o);
            if(request.cookie("username")!=null)
            {
                usuario = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));


            }
            else if(sql2oUsuarioDao.searchByUsername(request.session().attribute("username"))!=null && request.cookie("username")==null){
                usuario = sql2oUsuarioDao.searchByUsername(request.session().attribute("username"));}

            articulo.setAutor(usuario.getId());
           
            articulo.setFecha( new Date(new java.util.Date().getTime()));

            String etiquetas = (map.get("etiqueta").value());

            List<Etiqueta> etiq = new ArrayList<Etiqueta>();

            String[] ets = etiquetas.split(",");

         //   Long size2 = Long.parseLong(String.valueOf(size));


            for(String etiquet : etiquetas.split(",")){

                UUID uuid1 = UUID.randomUUID();
                Long key = uuid1.getMostSignificantBits();
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setId(key);
                etiqueta.setEtiqueta(etiquet);
                etiquetaDao.add(etiqueta);
                etiq.add(new Etiqueta(key, etiquet));
                System.out.println(key);
            }


           Etiqueta etiqueta = new Etiqueta();
            articulo.setEtiquetas(etiq);
            Long idarticulo = articuloDao.add(articulo);


            for(Etiqueta et : etiq){

                articuloDao.addTablaIntermedia(idarticulo, et);
            }



            response.redirect("/articulos");

            return "Ok";
        });




        get("/articulos/editar/:id", (request, response) -> {

            Long idarticulo = Long.parseLong(request.params("id"));
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;
            boolean autor=false;


            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }

            Articulo articulo = articuloDao.findOne(idarticulo);;

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("idarticulo", articulo.getId().toString());
            attributes.put("autenticado", autenticado);
            attributes.put("admin", admin);
            attributes.put("autor", autor);
            attributes.put("articulos", articulo);

            return new ModelAndView(attributes, "modificarArticulo.ftl");
        }, freeMarkerEngine);

        post("/articulos/editar/:id", (request, response) -> {

            Long idarticulo = Long.parseLong(request.params("id"));
            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            String etiquetas = request.queryParams("etiquetas");

            List<Etiqueta> etiq = new ArrayList<Etiqueta>();

//            for (String eti : etiquetas.split(",")) {
//                etiq.add(new Etiqueta(0L, eti));
//            }

            Articulo articulo = new Articulo(idarticulo, titulo, cuerpo, articuloDao.findOne(idarticulo).getAutorId(), new Date(new java.util.Date().getTime()), null, null);
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
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;
            boolean autor=false;


            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }

            attributes.put("usuarios", usuarioDao.getAll());
            attributes.put("autenticado", autenticado);
            attributes.put("admin", admin);
            attributes.put("autor", autor);


            return new ModelAndView(attributes, "gestionarUsuario.ftl");
        }, freeMarkerEngine);

        get("/articulos", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;
            boolean autor=false;


            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }


            List<Articulo> articulos = articuloDao.getAll();

            for(Articulo articulo : articulos){
                List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();

                List<Long> etiquetasids = articuloDao.obtenerEtiquetas(articulo.getId());

                for(Long ideti : etiquetasids){
                    etiquetas.add(etiquetaDao.findOne(ideti));
                }
                articulo.setEtiquetas(etiquetas);
            }

            attributes.put("articulos", articulos);
            attributes.put("autenticado", autenticado);
            attributes.put("admin", admin);
            attributes.put("autor", autor);




            return new ModelAndView(attributes, "gestionarArticulo.ftl");
        }, freeMarkerEngine);


        get("/usuarios/nuevo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;
            boolean autor=false;


            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }

            attributes.put("autor", autor);
            attributes.put("admin", admin);
            attributes.put("autenticado", autenticado);

            return new ModelAndView(attributes, "crearUsuario.ftl");
        }, freeMarkerEngine);


        post("usuarios/nuevo", (request, response) -> {

            QueryParamsMap map = request.queryMap();

            Usuario usuario = new Usuario();
            usuario.setUsername(map.get("username").value());
            usuario.setNombre(map.get("nombre").value());
            usuario.setPassword(map.get("password").value());

            System.out.println(request.queryParams("rol"));
            if(request.queryParams("rol").equals( "administrator")){

                usuario.setAdministrator(true);
                usuario.setAutor(false);

            }

            else if(request.queryParams("rol").equals("autor") ){

                usuario.setAdministrator(false);
                usuario.setAutor(true);
            }

            else{
                usuario.setAdministrator(false);
                usuario.setAutor(false);
            }

            Sql2oUsuarioDao usuarioDao1 = new Sql2oUsuarioDao(sql2o);
           if(usuarioDao1.searchByUsername(usuario.getUsername())==null){

               usuarioDao1.add(usuario);

               response.redirect("/usuarios");

               return null;
           }
          else {
            return "Usuario ya existe!";
        }

        });

        get("/usuarios/editar/:id", (request, response) -> {
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;
            boolean autor=false;


            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }

            Long idusuario = Long.parseLong(request.params("id"));

            Usuario usuario = usuarioDao.findOne(idusuario);


            Map<String, Object> attributes = new HashMap<>();

            attributes.put("autenticado", autenticado);
            attributes.put("admin", admin);
            attributes.put("autor", autor);
            attributes.put("idusuario", idusuario.toString());
            attributes.put("usuarios", usuario);

            return new ModelAndView(attributes, "modificarUsuario.ftl");
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


        get("/comentarios", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;
            boolean autor=false;


            if(request.cookie("username")!=null)
            {autenticado=true;
                Usuario user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));

                autor = user.isAutor();
                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                Usuario user = usuarioDao.searchByUsername(request.session().attribute("username"));

                autor = user.isAutor();
                admin = user.isAdministrator();
            }
            attributes.put("comentarios", comentarioDao.getAll());
            attributes.put("autenticado", autenticado);
            attributes.put("admin", admin);
            attributes.put("autor", autor);


            return new ModelAndView(attributes, "gestionarComentario.ftl");
        }, freeMarkerEngine);

        get("/comentarios/borrar/:id", (request, response) -> {

            Long idcomentario = Long.parseLong(request.params("id"));

            Comentario comentario = comentarioDao.findOne(idcomentario);

            if (comentario != null){
                comentarioDao.deleteById(idcomentario);
            }

            response.redirect("/");

            return null;

        },freeMarkerEngine);


        before("/articulos/nuevo/:id", (request, response) ->{

            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!(user.isAdministrator() || user.isAutor())){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );

        before("/articulos/editar/:id", (request, response) ->{

            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!(user.isAdministrator() || user.isAutor())){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );

        before("/articulos/borrar/:id", (request, response) ->{
            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!(user.isAdministrator() || user.isAutor())){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );

        before("/articulos", (request, response) ->{
            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                 user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }


            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!(user.isAdministrator() || user.isAutor())){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );

        before("/usuarios", (request, response) ->{

            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!user.isAdministrator()){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );

        before("/usuarios/nuevo/:id", (request, response) ->{

            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!user.isAdministrator()){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );

        before("/usuarios/editar/:id", (request, response) ->{

            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!user.isAdministrator()){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );

        before("/usuarios/borrar/:id", (request, response) ->{
            Usuario user = new Usuario();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(textEncryptor.decrypt(request.cookie("username")));
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!user.isAdministrator()){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );














//        get("login", (request, response) -> {
//            Map<String, Object> attributes = new HashMap<>();
//
//            return new ModelAndView(attributes, "formularioLogin.ftl");
//        }, freeMarkerEngine);
//
//
//        post("login", (request, response) -> {
//
//            QueryParamsMap map = request.queryMap();
//
//            Usuario usuario = usuarioDao.searchByUsername(map.get("username").value());
////
////            if(aqui va la logica del checkbox){
////
////                Session session = request.session();
////                session.attribute("username", map.get("username").value());
////                //setting session to expiry in 30 mins
////                session.maxInactiveInterval(604800);
////                response.cookie("username", map.get("username").value(), 604800);
////            }
//
//
//            if (map.get("username").value() == null || map.get("username").value().isEmpty()) {
//                return "Digite un nombre de usuario";
//            }
//
//            if (map.get("password").value() == null || map.get("password").value().isEmpty()) {
//                return "Digite una contrasena";
//            }
//
//            if(usuario != null){
//
//                if(map.get("password").value().equals(usuario.getPassword())){
//                    response.redirect("/inicio");
//
//                    return null;
//                }
//            }
//
//            return "Usuario o contrasena incorrecto";
//        });

    }
}

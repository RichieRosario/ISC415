
import dao.Sql2oUsuarioDao;
import encapsulacion.Usuario;
import freemarker.template.Configuration;
import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import hibernate.HibernateUtil;
import servicios.*;
import org.sql2o.Sql2o;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import Rutas.RutasWeb;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;



public class Main {

    public static void main(String[] args) throws SQLException {

       // Sql2o sql2o = new Sql2o( "jdbc:h2:~/blog", "", "");
        Hash hash = null;
        ConnectionService.startDb();
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        Spark.staticFileLocation("/public/");


//        DBService.getInstancia().testConexion();
//        ConnectionService.crearTablas();
//        ConnectionService.stopDb();

        HibernateUtil.buildSessionFactory().openSession().close();

        Sql2oUsuarioDao usuarioadmin = new Sql2oUsuarioDao(Usuario.class);
        if(usuarioadmin.searchByUsername("admin") == null){
        Usuario usuarioPorDefecto = new Usuario(1L, "admin", "Administrador", hash.sha1("admin"), true, false);
        usuarioadmin.add(usuarioPorDefecto);}

        HibernateUtil.openSession().close();

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        new RutasWeb(freeMarkerEngine);


    }
}
/*
import encapsulacion.Usuario;
import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import servicios.*;
import spark.QueryParamsMap;
import spark.Session;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;



public class Main {

    public static void main(String[] args) throws SQLException {

        // ConnectionService.startDb();
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");
        Spark.staticFileLocation("/public/");


        Spark.get("/", (request, response) -> {

            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));



            if(request.session().attribute("username") != null){
                autenticado = true;
            }

            au

            if(autenticado == true){



                StringWriter writer = new StringWriter();
                try {
                    Template get = configuration.getTemplate("templates/index.ftl");
                    Map<String, Object> map = new HashMap<>();
                    map.put("articulos",articulos);
                    map.put("admin",user.isAdministrator());
                    map.put("autor",user.isAutor());
                    map.put("autenticado",autenticado);
                    map.put("cantidadcomentarios",p.getComentarios().size());

                    get.process(map, writer);
                } catch (Exception e) {
                    Spark.halt(500);
                }
                return writer;
            }
            else{
                StringWriter writer = new StringWriter();
                try {
                    Template get = configuration.getTemplate("templates/index.ftl");
                    Map<String, Object> map = new HashMap<>();
                    map.put("articulos",articulos);
                    map.put("admin",false);
                    map.put("autor",false);
                    map.put("autenticado",false);
                    map.put("cantidadcomentarios",p.getComentarios().size());

                    get.process(map, writer);
                } catch (Exception e) {
                    Spark.halt(500);
                }
                return writer;
            }

        });

        Spark.get("/logout", (request,response) ->{
            List<Articulo> articulos = new ArrayList<Articulo>();
            Usuario user = new Usuario((long)1,"admin","Juan Pedro Belmonte García","admin",false,true);
            List<Comentario> comentarios = new ArrayList<Comentario>();
            List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
            Articulo p = new Articulo((long)1,"LeBron James: “Ha sido una de las derrotas más duras de mi carrera”\n","Cleveland Cavaliers desperdició una oportunidad única para comenzar las Finales de la NBA con una victoria. Entre el tiro libre de George Hill y la famosa jugada de J.R. Smith y una prórroga donde estuvieron desaparecidos, los jugadores de los Cavs no se lo han tomado nada bien. El primero, su principal líder y cara visible de la franquicia, LeBron James.  \n \n “Ha sido una de las derrotas más duras de mi carrera. Simplemente por todo lo que sucedió y la manera en que jugamos. Todos sabemos lo que sucedió al final del partido. Fueron 24 horas muy duras, no solo para George Hill o J.R. Smith, sino también para mí y para todo el equipo”.\n" +
                    "\n" +
                    "Cleveland tendrá otra oportunidad de dar un golpe sobre la mesa o ponerse completamente en contra en unas Finales donde no parten precisamente como favoritos para conseguir el título. En el segundo partido veremos cómo han afectado estas situaciones a toda la plantilla y si son capaces de volver con más fuerza ante unos Warriors que tratarán de subir el nivel.",user,Date.valueOf("2018-06-03"),comentarios,etiquetas);

            articulos.add(p);
            request.session().removeAttribute("username");
            response.redirect("/");
            StringWriter writer = new StringWriter();
            try {
                QueryParamsMap map2 = request.queryMap();


                Template formTemplate = configuration.getTemplate("templates/index.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("articulos",articulos);
                map.put("admin",user.isAdministrator());
                map.put("autor",user.isAutor());
                map.put("autenticado",true);
                map.put("cantidadcomentarios",p.getComentarios().size());

                formTemplate.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }
            return writer;




        });


        Spark.post("/", (request, response) -> {
            List<Articulo> articulos = new ArrayList<Articulo>();
            Usuario user = new Usuario((long)1,"admin","Juan Pedro Belmonte García","admin",false,true);
            List<Comentario> comentarios = new ArrayList<Comentario>();
            List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
            Articulo p = new Articulo((long)1,"LeBron James: “Ha sido una de las derrotas más duras de mi carrera”\n","Cleveland Cavaliers desperdició una oportunidad única para comenzar las Finales de la NBA con una victoria. Entre el tiro libre de George Hill y la famosa jugada de J.R. Smith y una prórroga donde estuvieron desaparecidos, los jugadores de los Cavs no se lo han tomado nada bien. El primero, su principal líder y cara visible de la franquicia, LeBron James.  \n \n “Ha sido una de las derrotas más duras de mi carrera. Simplemente por todo lo que sucedió y la manera en que jugamos. Todos sabemos lo que sucedió al final del partido. Fueron 24 horas muy duras, no solo para George Hill o J.R. Smith, sino también para mí y para todo el equipo”.\n" +
                    "\n" +
                    "Cleveland tendrá otra oportunidad de dar un golpe sobre la mesa o ponerse completamente en contra en unas Finales donde no parten precisamente como favoritos para conseguir el título. En el segundo partido veremos cómo han afectado estas situaciones a toda la plantilla y si son capaces de volver con más fuerza ante unos Warriors que tratarán de subir el nivel.",user,Date.valueOf("2018-06-03"),comentarios,etiquetas);

            articulos.add(p);

            String username = request.queryParams("username") != null ? request.queryParams("username") : "";
            String password = request.queryParams("password") != null ? request.queryParams("password") : "";
            String remember = request.queryParams("remember") != null ? request.queryParams("remember") : "";


            System.out.println(username);
            if( user.getUsername().equals(username) && user.getPassword().equals(password)){

            StringWriter writer = new StringWriter();

            try {
                QueryParamsMap map2 = request.queryMap();
                Session session = request.session();
                session.attribute("username", map2.get("username").value());
                session.maxInactiveInterval(604800);
                if(!remember.isEmpty()){

                response.cookie("username", map2.get("username").value(), 604800); }

                Template formTemplate = configuration.getTemplate("templates/index.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("articulos",articulos);
                map.put("admin",user.isAdministrator());
                map.put("autor",user.isAutor());
                map.put("autenticado",true);
                map.put("cantidadcomentarios",p.getComentarios().size());

                formTemplate.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }
            return writer;
        }
        else{
                StringWriter writer = new StringWriter();
                try {
                    Template formTemplate = configuration.getTemplate("templates/index.ftl");
                    Map<String, Object> map = new HashMap<>();
                    map.put("articulos",articulos);
                    map.put("autenticado",false);
                    map.put("cantidadcomentarios",p.getComentarios().size());

                    formTemplate.process(map, writer);
                } catch (Exception e) {
                    Spark.halt(500);
                }
                return writer;
            }

        });
    }

}*/
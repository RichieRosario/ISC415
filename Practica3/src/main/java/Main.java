
import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import servicios.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
        List<Articulo> articulos = new ArrayList<Articulo>();
        Usuario user = new Usuario((long)1,"admin","Juan Pedro Belmonte García","admin",true,true);
        List<Comentario> comentarios = new ArrayList<Comentario>();
        List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
         Articulo p = new Articulo((long)1,"LeBron James: “Ha sido una de las derrotas más duras de mi carrera”\n","Cleveland Cavaliers desperdició una oportunidad única para comenzar las Finales de la NBA con una victoria. Entre el tiro libre de George Hill y la famosa jugada de J.R. Smith y una prórroga donde estuvieron desaparecidos, los jugadores de los Cavs no se lo han tomado nada bien. El primero, su principal líder y cara visible de la franquicia, LeBron James.  \n \n “Ha sido una de las derrotas más duras de mi carrera. Simplemente por todo lo que sucedió y la manera en que jugamos. Todos sabemos lo que sucedió al final del partido. Fueron 24 horas muy duras, no solo para George Hill o J.R. Smith, sino también para mí y para todo el equipo”.\n" +
                 "\n" +
                 "Cleveland tendrá otra oportunidad de dar un golpe sobre la mesa o ponerse completamente en contra en unas Finales donde no parten precisamente como favoritos para conseguir el título. En el segundo partido veremos cómo han afectado estas situaciones a toda la plantilla y si son capaces de volver con más fuerza ante unos Warriors que tratarán de subir el nivel.",user,Date.valueOf("2018-06-03"),comentarios,etiquetas);

        articulos.add(p);


            Articulo p2 = new Articulo((long)1,"probando2","aseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakase",user,Date.valueOf("2000-11-01"),comentarios,etiquetas);
            articulos.add(p2);
            Articulo p3 = new Articulo((long)1,"probando2","aseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakase",user,Date.valueOf("2000-11-01"),comentarios,etiquetas);
            articulos.add(p3);
            Articulo p4 = new Articulo((long)1,"probando2","aseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakaseolakase",user,Date.valueOf("2000-11-01"),comentarios,etiquetas);
            articulos.add(p4);
            StringWriter writer = new StringWriter();
            try {
                Template formTemplate = configuration.getTemplate("templates/post.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("articulos",articulos);
                map.put("autenticado",false);


                formTemplate.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }






            return writer;
        });
      //  DBService.getInstancia().testConexion();
       // ConnectionService.crearTablas();
       // ConnectionService.stopDb();
    }
}
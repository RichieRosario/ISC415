
import encapsulacion.Articulo;
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

         Articulo p = new Articulo(1,);
        p.setTitulo("PRobando");
        p.setCuerpo("ola k ase");
        p.setFecha(Date.valueOf("20-05-2018"));
        articulos.add(p);
            StringWriter writer = new StringWriter();
            try {
                Template formTemplate = configuration.getTemplate("templates/index.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("articulos",articulos);


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

import dao.Sql2oUsuarioDao;
import encapsulacion.Usuario;
import freemarker.template.Configuration;
import encapsulacion.Articulo;
import encapsulacion.Usuario;
import servicios.*;

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

       // ConnectionService.startDb();
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");
        Spark.staticFileLocation("/public/");
      //  Spark.get("/", (request, response) -> {


      //  DBService.getInstancia().testConexion();
       // ConnectionService.crearTablas();
       // ConnectionService.stopDb();
   // }
}
}
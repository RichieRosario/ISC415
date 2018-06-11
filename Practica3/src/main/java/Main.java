
import dao.Sql2oUsuarioDao;
import encapsulacion.Usuario;
import freemarker.template.Configuration;
import servicios.*;
import java.sql.SQLException;
import java.util.List;

import Rutas.RutasWeb;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

    public static void main(String[] args) throws SQLException {

        ConnectionService.startDb();

        DBService.getInstancia().testConexion();

        ConnectionService.stopDb();

        Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/");

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        new RutasWeb(freeMarkerEngine);
        }
}
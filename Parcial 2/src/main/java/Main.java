

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import hibernate.HibernateUtil;
import servicios.ConnectionService;
import spark.Spark;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import spark.template.freemarker.FreeMarkerEngine;

import static spark.Spark.staticFileLocation;


public class Main {

    public static void main(String[] args) {

        try{
            ConnectionService.startDb();
        }
        catch (SQLException e){

            e.printStackTrace();
        }
        final Configuration configuration = new Configuration(new Version(2, 3, 26));

        configuration.setClassForTemplateLoading(Main.class, "/templates");

        staticFileLocation("/public/");


        HibernateUtil.buildSessionFactory().openSession().close();

        HibernateUtil.openSession().close();


    }
}
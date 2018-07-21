

import freemarker.template.Configuration;
import Rutas.*;
import freemarker.template.Template;
import freemarker.template.Version;
import hibernate.HibernateUtil;
import servicios.ConnectionService;
import spark.Spark;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import dao.*;
import modelo.*;
import servicios.*;
import spark.template.freemarker.FreeMarkerEngine;

import static spark.Spark.staticFileLocation;


public class Main {


    public static void main(String[] args) {

        UserDaoImpl usuarioadmin = new UserDaoImpl(User.class);
        ProfileDaoImpl profileadmin = new ProfileDaoImpl(Profile.class);


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
        User temp = usuarioadmin.searchByUsername("admin");
        System.out.println(temp);
        if(temp == null){
            System.out.println("hola");
            User usuarioPorDefecto = new User(1, "admin", "admin", "admin@gwebmaster.me",true,null,null,null);
                      usuarioadmin.add(usuarioPorDefecto);
                      Profile perfil = new Profile();
                      perfil.setUser(usuarioPorDefecto);
                      profileadmin.add(perfil);


        }

        HibernateUtil.openSession().close();

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
        new RutasWeb(freeMarkerEngine);

    }
}
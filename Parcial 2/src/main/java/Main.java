

import freemarker.template.Configuration;
import Rutas.*;
import freemarker.template.Template;
import freemarker.template.Version;
import hibernate.HibernateUtil;
import servicios.ConnectionService;
import spark.Spark;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import dao.*;
import modelo.*;
import servicios.*;
import spark.template.freemarker.FreeMarkerEngine;

import javax.imageio.ImageIO;

import static spark.Spark.staticFileLocation;


public class Main {


    public static void main(String[] args) throws IOException {

        UserDaoImpl usuarioadmin = new UserDaoImpl(User.class);
        ProfileDaoImpl profileadmin = new ProfileDaoImpl(Profile.class);
        WallDaoImpl wallDao = new WallDaoImpl(Wall.class);
        EventDaoImpl eventoDao = new EventDaoImpl(Event.class);


        try{
            ConnectionService.startDb();
        }
        catch (SQLException e){

            e.printStackTrace();
        }
        final Configuration configuration = new Configuration(new Version(2, 3, 26));

        configuration.setClassForTemplateLoading(Main.class, "/templates");

        staticFileLocation("/public");



        HibernateUtil.buildSessionFactory().openSession().close();
        User temp = usuarioadmin.searchByUsername("admin");

        if(temp == null){
            User usuarioPorDefecto = new User(1, "admin", "admin", "admin@gwebmaster.me",true,null,null,null);
                      usuarioadmin.add(usuarioPorDefecto);
                      Profile perfil = new Profile();
                      perfil.setUser(usuarioPorDefecto);
                      perfil.setSexo('M');
                      perfil.setLugartrabajo("PUCMM");
                      perfil.setLugarnacimiento("Santiago De Los Caballeros, Republica Dominicana");
                      perfil.setLugarestudio("PUCMM");
                      perfil.setNombre("John");
                      perfil.setApellido("Doe");
                      perfil.setFechanacimiento(new Date(1980,10,10));
                      perfil.setCiudadactual("Santo Domingo, Republica Dominicana");
            BufferedImage imagen = null;
            File here = new File(".");

                String path = here.getCanonicalPath()+"/src/main/resources/public/img/johndoe.jpg";

            try {
                imagen = ImageIO.read(new File((path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream imagenb = new ByteArrayOutputStream();
            try {
                ImageIO.write(imagen, "jpg",imagenb);
            } catch (IOException e) {
                e.printStackTrace();
            }


            perfil.setProfilepic(imagenb.toByteArray());
                      profileadmin.add(perfil);

                      Wall wall = new Wall();
                      wall.setUser(usuarioPorDefecto);
            wallDao.add(wall);

            Event evento = new Event();
            evento.setEvento(perfil.getNombre()+" "+perfil.getApellido()+" se ha unido a Una Red Social");
            evento.setUser(usuarioPorDefecto);
            evento.setFecha(LocalDate.now());
            evento.setWall(wall);
            eventoDao.add(evento);


        }

        HibernateUtil.openSession().close();

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
        new RutasWeb(freeMarkerEngine);

    }
}
package Rutas;

import dao.*;
import hibernate.HibernateUtil;
import javafx.geometry.Pos;
import modelo.*;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Session;
import spark.utils.IOUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.text.*;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Cookie;
import javax.servlet.http.Part;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static spark.Spark.*;

import static spark.Spark.get;

public class RutasWeb {
    public RutasWeb(final FreeMarkerEngine freeMarkerEngine) {

        UserDaoImpl usuarioDao;
        FriendshipDaoImpl friendshipDao;
        CommentDaoImpl commentDao;
        NotificationDaoImpl notificationDao;
        PhotoDaoImpl photoDao;
        PostDaoImpl postDao;
        ProfileDaoImpl profileDao;
        TagDaoImpl tagDao;
        AlbumDaoImpl albumDao;
        WallDaoImpl wallDao;
        EventDaoImpl eventDao;
        LikeDislikeDaoImpl likeDislikeDao;


        usuarioDao = new UserDaoImpl(User.class);
        friendshipDao = new FriendshipDaoImpl(Friendship.class);
        commentDao = new CommentDaoImpl(Comment.class);
        notificationDao = new NotificationDaoImpl(Notification.class);
        photoDao = new PhotoDaoImpl(Photo.class);
        postDao = new PostDaoImpl(Post.class);
        profileDao = new ProfileDaoImpl(Profile.class);
        tagDao = new TagDaoImpl(Tag.class);
        albumDao = new AlbumDaoImpl(Album.class);
        wallDao = new WallDaoImpl(Wall.class);
        eventDao = new EventDaoImpl(Event.class);
        likeDislikeDao = new LikeDislikeDaoImpl(LikeDislike.class);

        File uploadDir = new File("upload");
        uploadDir.mkdir(); //


        before("/usuarios", (request, response) ->{

            User user = new User();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(request.cookie("username"));
            }
            else if(request.cookie("username")==null){
                response.redirect("/");
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!user.isAdministrator()){
                    halt(401,"No tienes permisos para acceder.");
                }}

        } );

        before("/usuarios/editar/:id", (request, response) ->{

            User user = new User();
            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(request.cookie("username"));
            }
            else if(request.cookie("username")==null){
                response.redirect("/");
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

            User user = new User();

            if(request.cookie("username")!=null)
            {
                user = usuarioDao.searchByUsername(request.cookie("username"));
            }
            else if(request.cookie("username")==null){
                response.redirect("/");
            }

            if(user==null){
                halt(403,"Forbidden Access.");
            }
            else{
                if(!user.isAdministrator()){
                    halt(401,"No tiene permisos para hacer esta acción.");
                }}

        } );


        //Rutas Inicio
        get("/home", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();

            User user = new User();
            if(request.cookie("username")!=null) {
                user = usuarioDao.searchByUsername(request.cookie("username").toString());

                attributes.put("usuario", user);
                attributes.put("perfil", user.getUsername());
                attributes.put("admin", user.isAdministrator());
                List<Integer> friendsids = friendshipDao.getAllFriends(user);
                List<User> friends = usuarioDao.getUsersById(friendsids);
                List<Profile> friendsProfiles = new ArrayList<>();
                Wall muro = new Wall();

                for(User user1 : friends){
                    muro.getEvents().addAll(user1.getWall().getEvents());

                    muro.getPosts().addAll(user1.getWall().getPosts());

                }

                List<Notification> notificationList = notificationDao.unseenNotifications(user);
                attributes.put("unseen",notificationList.size());

                attributes.put("muroeventos", muro.getEvents());
                attributes.put("muroentradas", muro.getPosts());
            }
            else{
                response.redirect("/");
            }
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/notifications", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();

            User user = new User();
            if(request.cookie("username")!=null) {
                user = usuarioDao.searchByUsername(request.cookie("username"));

                attributes.put("usuario", user);
                attributes.put("perfil", user.getUsername());
                attributes.put("admin", user.isAdministrator());
                List<Integer> friendsids = friendshipDao.getAllFriends(user);
                List<User> friends = usuarioDao.getUsersById(friendsids);
                List<Profile> friendsProfiles = new ArrayList<>();
                Wall muro = new Wall();

                for(User user1 : friends){
                    muro.getEvents().addAll(user1.getWall().getEvents());

                    muro.getPosts().addAll(user1.getWall().getPosts());

                }

                List<Notification> notificationList = notificationDao.unseenNotifications(user);
                for(Notification no : notificationList){
                 no.setSeen(true);
                 notificationDao.update(no);
                }
                attributes.put("unseen",notificationList.size());
                attributes.put("notifications", notificationList);


                attributes.put("muroeventos", muro.getEvents());
                attributes.put("muroentradas", muro.getPosts());
            }
            else{
                response.redirect("/");
            }
            return new ModelAndView(attributes, "notifications.ftl");
        }, freeMarkerEngine);


        //Rutas amigos
        get("/friends", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba
            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();

                for(User user1 : amigos) {
                    Profile profile = user1.getProfile();
                    profilesList.add(profile);
                }

            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());

            return new ModelAndView(attributes, "friends.ftl");
        }, freeMarkerEngine);

        get("/friendRequests", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username"));

            List<Integer> friendRequests = friendshipDao.getFriendRequests(user);
            List<User> solicitudes = usuarioDao.getUsersById(friendRequests);

            ArrayList<Profile> profilesList = new ArrayList<>();
            attributes.put("usuario", user);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("solicitudes", solicitudes);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);
            attributes.put("unseen",notificationList.size());

                List<User> personList = usuarioDao.getUsersById(friendRequests);

                for(User user1 : personList){
                    Profile profile = user1.getProfile();
                    profilesList.add(profile);
                }
                attributes.put("profilesList", profilesList);



            return new ModelAndView(attributes, "friendRequests.ftl");
        }, freeMarkerEngine);

        get("/findFriends", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username"));


            ArrayList<Profile> profilesList = new ArrayList<>();

            attributes.put("usuario", user);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());
            List<User> personlist = new ArrayList<>();


            List<Integer> pr = new ArrayList<>();
            pr=friendshipDao.getFriendRequests(user);
            List<Integer> pr2 = new ArrayList<>();
            pr2=friendshipDao.getPendingRequests(user);

                for(Profile user2: profileDao.getAll()){
                    if((user2.getUser().getUsername() != user.getUsername())) {
                        if (user.getProfile().getLugarestudio().contains(user2.getLugarestudio())) {
                            profilesList.add(user2);
                        } else if ((user.getProfile().getCiudadactual().contains(user2.getCiudadactual()))) {
                            profilesList.add(user2);
                        } else if ((user.getProfile().getLugartrabajo().contains(user2.getLugarnacimiento()))) {
                            profilesList.add(user2);
                        } else if ((user.getProfile().getLugarnacimiento().contains(user2.getLugarnacimiento()))) {
                            profilesList.add(user2);
                        }

                    }

                    if(pr.contains(user2.getUser().getId()) || pr2.contains(user2.getUser().getId()) || friendshipDao.checkIfFriend(user, user2.getUser().getId())){
                        profilesList.remove(user2);
                    }
                }



            attributes.put("perfiles", profilesList);



            return new ModelAndView(attributes, "findfriends.ftl");
        }, freeMarkerEngine);

        get("/pendingRequests", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username"));
            List<Integer> pendingRequests = new ArrayList<>();
            pendingRequests = friendshipDao.getPendingRequests(user);
            for (Integer integer: pendingRequests) {
                System.out.println(integer);
            }
            List<User> solicitudesPendientes = usuarioDao.getUsersById(pendingRequests);
            ArrayList<Profile> profilesList = new ArrayList<>();

            attributes.put("usuario", user);
            attributes.put("profile", user.getProfile());
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("solicitudesPendientes", solicitudesPendientes);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());


                List<User> personList = new ArrayList<User>();
                personList = usuarioDao.getUsersById(pendingRequests);

            for(User user1 : personList){
                Profile profile = user1.getProfile();
                profilesList.add(profile);
            }
            attributes.put("profilesList", profilesList);

            return new ModelAndView(attributes, "pendingRequests.ftl");
        }, freeMarkerEngine);

        post("/sendRequest/:userId", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba

            String userId = request.params("userId");
            friendshipDao.sendFriendRequest(user, Integer.parseInt(userId));

            Notification n = new Notification();
            n.setSeen(false);
             n.setNotificacion(user.getUsername()+" Te ha enviado una solicitud de amistad");
            n.setUser(usuarioDao.findOne(Integer.parseInt(userId)));
            notificationDao.add(n);

           response.redirect("/");
           return null;
        });
        post("/acceptRequest", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username"));//prueba

            String userId = request.queryParams("accept");
            System.out.println(userId);
            friendshipDao.acceptRequest(user, Integer.parseInt(userId));
            response.redirect("/friends");
            return null;
        });
        post("/unFriend", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba

            String userId = request.queryParams("decline");
            friendshipDao.unFriend(user, Integer.parseInt(userId));
            response.redirect("/friends");
            return null;
        });

        //Rutas profile
        get("/profile/:username", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            boolean owner = false;

            String username = request.params("username");

            if(request.cookie("username")!=null) {
                User user = usuarioDao.searchByUsername(username);
                boolean isPending = false;
                User usuarioLogueado = usuarioDao.searchByUsername(request.cookie("username"));
                boolean isFriend = friendshipDao.checkIfFriend(usuarioLogueado, user.getId());
                if(usuarioLogueado.getUsername().equals(user.getUsername())) owner=true;
                if(friendshipDao.getFriendRequests(user).contains(usuarioLogueado.getId()) ){
                    isPending=true;
                }
                attributes.put("owner",owner);
                attributes.put("isPending",isPending);
                attributes.put("usuario", usuarioLogueado);
                attributes.put("perfil", usuarioDao.getProfile(user));
                attributes.put("admin", usuarioLogueado.isAdministrator());
                List<Post> posts = postDao.getMyPosts(user);
                List<Notification> notificationList = notificationDao.unseenNotifications(usuarioLogueado);
                attributes.put("unseen",notificationList.size());
                attributes.put("user", user);
                attributes.put("posts", posts);
                attributes.put("isFriend", isFriend);

            List<Integer> friendsids = friendshipDao.getAllFriends(user);
            List<User> friends = usuarioDao.getUsersById(friendsids);
            List<Profile> friendsProfiles = new ArrayList<>();

            for(User user1 : friends){
                friendsProfiles.add(usuarioDao.getProfile(user1));
            }

            Wall muro = user.getWall();
            attributes.put("muroeventos", muro.getEvents());
            attributes.put("muroentradas", muro.getPosts());
            attributes.put("eventoscomentarios", muro.getEvents());
            attributes.put("albums", user.getAlbums());
            attributes.put("totalFriends", friendsids.size());
            attributes.put("perfiles", friendsProfiles);
                List<Integer> friendlist = friendshipDao.getAllFriends(usuarioLogueado);
                List<User> amigos = usuarioDao.getUsersById(friendlist);
                ArrayList<Profile> profilesList = new ArrayList<>();

                for(User user1 : amigos) {
                    Profile profile = user1.getProfile();
                    profilesList.add(profile);
                }

                attributes.put("amigos",amigos);
            }

            return new ModelAndView(attributes, "profile.ftl");
        }, freeMarkerEngine);

        post("/subirfoto","multipart/form-data",  (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            long maxFileSize = 100000000;
            long maxRequestSize = 100000000;
            int fileSizeThreshold = 1024;

            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                    uploadDir.getAbsolutePath(), maxFileSize, maxRequestSize, fileSizeThreshold);
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    multipartConfigElement);



            Part uploadedFile = request.raw().getPart("uploaded_file");

            String fName = request.raw().getPart("uploaded_file").getSubmittedFileName();

                    java.nio.file.Path out = Paths.get(uploadDir.getCanonicalPath() +"/" +fName);
                    InputStream in = uploadedFile.getInputStream();
                        Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                        uploadedFile.delete();

                    multipartConfigElement = null;
                    uploadedFile = null;

            BufferedImage imagen = null;
            File here = new File(".");

            String path = uploadDir.getCanonicalPath() +"/" +fName;
            System.out.println(path);

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

            User usuario = usuarioDao.searchByUsername(request.cookie("username"));

            Profile profile = usuarioDao.getProfile(usuario);
            profile.setProfilepic(imagenb.toByteArray());
            profileDao.update(profile);


            response.redirect("/profile/"+usuario.getUsername());
            return null;
        });



        //Rutas index
        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            boolean autenticado = false;
            QueryParamsMap map2 = request.queryMap();
            boolean administrator = false;
            User user = new User();



            if(request.cookie("username")!=null){

                user = usuarioDao.searchByUsername(request.cookie("username"));
                response.redirect("/home");


            }


            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);


        post("/login", (request, response) -> {
             Map<String, Object> attributes = new HashMap<>();
            String username = request.queryParams("userpost");
            String password = request.queryParams("passpost");
            User user = usuarioDao.searchByUsername(username);

            if( user.getPassword().equals(password)) {
                Session session = request.session();
                session.attribute("username", username);
                response.cookie("username", username, 604800);
            }
                response.redirect("/");

            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        //Rutas Logout
        get("/logout", (request,response) ->{
            Session session = request.session();
            session.removeAttribute("username");
            response.removeCookie("username");
            response.redirect("/");

            Map<String, Object> attributes = new HashMap<>();

            attributes.put("usuariodentro","Huesped");
            attributes.put("admin", false);
            attributes.put("autenticado", false);

            return new ModelAndView(attributes, "index.ftl");
        },freeMarkerEngine);

        //Rutas Usuarios
        get("/usuarios", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean administrator = false;


            if(request.cookie("username")!=null) {
                autenticado = true;
                User user = usuarioDao.searchByUsername(request.cookie("username"));

                administrator = user.isAdministrator();

                    attributes.put("usuario", user);


                attributes.put("usuarios", usuarioDao.getAll());
                attributes.put("perfil", user.getUsername());
                attributes.put("admin", administrator);
                List<Notification> notificationList = notificationDao.unseenNotifications(user);

                attributes.put("unseen",notificationList.size());
            }
            else{
                response.redirect("/");
            }

            return new ModelAndView(attributes, "gestionarUsuarios.ftl");
        }, freeMarkerEngine);

        get("/usuarios/editar/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            User user = usuarioDao.searchByUsername(request.cookie("username"));
            Integer idusuario = Integer.parseInt(request.params("id"));

            User usuario = usuarioDao.findOne(idusuario);


            Map<String, Object> attributes = new HashMap<>();

            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("idusuario", idusuario.toString());
            attributes.put("user", usuario);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());

            return new ModelAndView(attributes, "modificarUsuario.ftl");
        }, freeMarkerEngine);
        post("/usuarios/editar/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            SimpleDateFormat format = new SimpleDateFormat("yy-mm-dd");
            Integer idusuario = Integer.parseInt(request.params("id"));
            String username = request.queryParams("username");
            String nombre = request.queryParams("nombre");
            String password = request.queryParams("password");
            Boolean administrator = Boolean.parseBoolean(request.queryParams("rol"));
            String email = request.queryParams("email");
            String apellido = request.queryParams("apellido");
            String ciudadactual = request.queryParams("ciudadactual");
            String lugartrabajo = request.queryParams("lugartrabajo");
            java.util.Date fechanacimiento = format.parse(request.queryParams("date"));
            String lugarestudio = request.queryParams("lugarestudio");
            String lugarnacimiento = request.queryParams("lugarnacimiento");
            Character sexo = request.queryParams("sexo").charAt(0);
            User usuario = usuarioDao.findOne(idusuario);
            usuario.setAdministrator(administrator);
            usuario.setEmail(email);
            usuario.setUsername(username);
            usuario.setPassword(password);
            Profile profile = usuario.getProfile();
            profile = new Profile(profile.getId(), nombre, apellido,fechanacimiento, lugarnacimiento, ciudadactual, lugarestudio, lugartrabajo, sexo);
            profileDao.update(profile);
            usuarioDao.update(usuario);

            response.redirect("/home");

            return null;
        });
        get("/usuarios/borrar/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            Integer idusuario = Integer.parseInt(request.params("id"));

            User usuario = usuarioDao.findOne(idusuario);
            if (usuario != null){
                usuarioDao.deleteById(usuario);
            }

            response.redirect("/usuarios");

            return null;

        },freeMarkerEngine);

        //Rutas Registrarse
        post("/registrarse", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();

           String username = request.queryParams("username");
            String password = request.queryParams("password");
            String nombre = request.queryParams("nombre");
            String apellido = request.queryParams("apellido");
            String sexo = request.queryParams("sexo");
            String email = request.queryParams("email");
            String lugarresidencia = request.queryParams("lugarresidencia");
            String lugarnacimiento = request.queryParams("lugarnacimiento");
            SimpleDateFormat format = new SimpleDateFormat("yy-mm-dd");
            java.util.Date date = format.parse(request.queryParams("date"));
            String lugarestudio = request.queryParams("lugarestudio");
            String lugartrabajo = request.queryParams("lugartrabajo");

            User newuser = new User();
            newuser.setAdministrator(false);
            newuser.setUsername(username);
            newuser.setPassword(password);
            newuser.setEmail(email);
            usuarioDao.add(newuser);
            Profile newprofile = new Profile();
            newprofile.setNombre(nombre);
            newprofile.setSexo(sexo.charAt(0));
            newprofile.setApellido(apellido);
            newprofile.setCiudadactual(lugarresidencia);
            newprofile.setFechanacimiento(date);
            newprofile.setLugarestudio(lugarestudio);
            newprofile.setLugarnacimiento(lugarnacimiento);
            newprofile.setLugartrabajo(lugartrabajo);
            newprofile.setUser(newuser);
            File here = new File(".");
            BufferedImage imagen = null;

            String path = here.getCanonicalPath()+"/src/main/resources/public/img/profile-default.png";

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


           newprofile.setProfilepic(imagenb.toByteArray());
            profileDao.add(newprofile);
            Wall newmuro = new Wall();
            newmuro.setUser(newuser);
            wallDao.add(newmuro);

            Event evento = new Event();
            evento.setWall(newmuro);
            evento.setUser(newuser);
            evento.setFecha(LocalDate.now());
            evento.setEvento(newprofile.getNombre()+" "+newprofile.getApellido()+" se ha unido a Una Red Social");
            eventDao.add(evento);

            response.redirect("/");

            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        get("/album/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba
            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();
            Album album = albumDao.findOne(id);
            boolean owner = false;
            if(user.getUsername().equals(album.getUser().getUsername())) owner=true;

            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("owner", owner);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            attributes.put("album", album);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());
            return new ModelAndView(attributes, "verAlbum.ftl");

        }, freeMarkerEngine);

        get("/photo/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba
            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();
            Photo album = photoDao.findOne(id);
            boolean owner = false;
            if(user.getUsername().equals(album.getAlbums().getUser().getUsername())) owner=true;

            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("owner", owner);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            attributes.put("foto", album);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());

            return new ModelAndView(attributes, "verFoto.ftl");

        }, freeMarkerEngine);

        get("/editarFoto/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba
            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();
            Photo album = photoDao.findOne(id);
            boolean owner = false;
            if(user.getUsername().equals(album.getAlbums().getUser().getUsername())) owner=true;

            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("owner", owner);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            attributes.put("foto", album);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());

            return new ModelAndView(attributes, "editarFoto.ftl");

        }, freeMarkerEngine);

        post("/editarFoto/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba
            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();
            Photo album = photoDao.findOne(id);
            boolean owner = false;
            if(user.getUsername().equals(album.getAlbums().getUser().getUsername())) owner=true;
            album.setCaption(request.queryParams("caption"));
            String useret = request.queryParams("amigos[]");
            Tag t = new Tag();
            User usuarioetiquetado = usuarioDao.searchByUsername(useret);
            t.setUsers(usuarioetiquetado);
            album.setEtiqueta(t);
            tagDao.add(t);
            photoDao.update(album);
            Notification n = new Notification();
            n.setSeen(false);
            n.setNotificacion(user+ " Te ha etiquetado en una foto");
            n.setUser(usuarioetiquetado);
            notificationDao.add(n);



            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("owner", owner);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            attributes.put("foto", album);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());

            response.redirect("/photo/"+id);

            return new ModelAndView(attributes, "editarFoto.ftl");

        }, freeMarkerEngine);

        get("/editarAlbum/:id", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba
            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();
            Album album = albumDao.findOne(id);
            boolean owner = false;
            if(user.getUsername().equals(album.getUser().getUsername())) owner=true;

            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("owner", owner);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            attributes.put("album", album);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());
            return new ModelAndView(attributes, "editarAlbum.ftl");

        }, freeMarkerEngine);

        get("/crearAlbum", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba
            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();

            for(User user1 : amigos) {
                Profile profile = user1.getProfile();
                profilesList.add(profile);
            }

            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("unseen",notificationList.size());


            return new ModelAndView(attributes, "crearAlbum.ftl");

        }, freeMarkerEngine);


        post("/crearAlbum", "multipart/form-data", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            long maxFileSize = 100000000;
            long maxRequestSize = 100000000;
            int fileSizeThreshold = 1024;

            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                    uploadDir.getAbsolutePath(), maxFileSize, maxRequestSize, fileSizeThreshold);
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    multipartConfigElement);


            Part name = request.raw().getPart("nombre");
            Part uploadedFile = request.raw().getPart("uf");

            Part descr = request.raw().getPart("descripcion");
            System.out.println(uploadedFile.getSubmittedFileName());
            if(!uploadedFile.getSubmittedFileName().isEmpty()) {
                String fName = request.raw().getPart("uf").getSubmittedFileName();
                System.out.println("entre");
                java.nio.file.Path out = Paths.get(uploadDir.getCanonicalPath() + "/" + fName);
                InputStream in = uploadedFile.getInputStream();
                Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                uploadedFile.delete();

                multipartConfigElement = null;
                uploadedFile = null;

                BufferedImage imagen = null;
                File here = new File(".");

                String path = uploadDir.getCanonicalPath() + "/" + fName;
                System.out.println(path);

                try {
                    imagen = ImageIO.read(new File((path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream imagenb = new ByteArrayOutputStream();
                try {
                    ImageIO.write(imagen, "jpg", imagenb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boolean autenticado=false;
                Photo fpost = new Photo();


                String s = request.queryParams("amigos[]");
                System.out.println(s);
               Album album = new Album();

                User log = usuarioDao.searchByUsername(request.cookie("username"));
                Tag t = new Tag();
                User usuarioetiquetado = usuarioDao.searchByUsername(s);
                t.setUsers(usuarioetiquetado);
                album.setEtiqueta(t);
                tagDao.add(t);
                Notification n = new Notification();
                n.setSeen(false);
                n.setNotificacion(log.getUsername()+ " Te ha etiquetado en un albúm");
                n.setUser(usuarioetiquetado);
                notificationDao.add(n);
               album.setNombre(IOUtils.toString(name.getInputStream()));
                album.setUser(log);
                album.setNombredescripcion(IOUtils.toString(descr.getInputStream()));

                albumDao.add(album);

                fpost.setFoto(imagenb.toByteArray());
                fpost.setAlbums(album);

                photoDao.add(fpost);


                response.redirect("/profile/" + log.getUsername());

            }
            return "Ok";
        });


        post("/editarAlbum/:id", "multipart/form-data", (request, response) -> {

            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            int id = Integer.parseInt(request.params("id"));
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            long maxFileSize = 100000000;
            long maxRequestSize = 100000000;
            int fileSizeThreshold = 1024;

            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                    uploadDir.getAbsolutePath(), maxFileSize, maxRequestSize, fileSizeThreshold);
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    multipartConfigElement);


            Part name = request.raw().getPart("nombre");
            Part uploadedFile = request.raw().getPart("uf");

            Part descr = request.raw().getPart("descripcion");

             if(!uploadedFile.getSubmittedFileName().isEmpty()) {
                String fName = request.raw().getPart("uf").getSubmittedFileName();
                System.out.println("entre");
                java.nio.file.Path out = Paths.get(uploadDir.getCanonicalPath() + "/" + fName);
                InputStream in = uploadedFile.getInputStream();
                Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                uploadedFile.delete();

                multipartConfigElement = null;
                uploadedFile = null;

                BufferedImage imagen = null;
                File here = new File(".");

                String path = uploadDir.getCanonicalPath() + "/" + fName;
                System.out.println(path);

                try {
                    imagen = ImageIO.read(new File((path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream imagenb = new ByteArrayOutputStream();
                try {
                    ImageIO.write(imagen, "jpg", imagenb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boolean autenticado=false;
                Photo fpost = new Photo();


                String s = request.queryParams("amigos[]");
                Album album = albumDao.findOne(id);

                User log = usuarioDao.searchByUsername(request.cookie("username"));
                 Tag t = new Tag();
                 User usuarioetiquetado = usuarioDao.searchByUsername(s);
                 t.setUsers(usuarioetiquetado);
                 album.setEtiqueta(t);
                 tagDao.add(t);
                 Notification n = new Notification();
                 n.setSeen(false);
                 n.setNotificacion(log.getUsername()+ " Te ha etiquetado en un albúm");
                 n.setUser(usuarioetiquetado);
                 notificationDao.add(n);
                 album.setNombre(IOUtils.toString(name.getInputStream()));
                 album.setNombredescripcion(IOUtils.toString(descr.getInputStream()));



                 fpost.setFoto(imagenb.toByteArray());
                 fpost.setAlbums(album);

                 photoDao.add(fpost);
                 albumDao.update(album);


                response.redirect("/album/" + id);

            }
            return "Ok";
        });


        //Rutas Likes
        post("/like/post/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            boolean autenticado=false;
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User usuario = new User();

                usuario = usuarioDao.searchByUsername(request.cookie("username"));


            LikeDislike valoracion = new LikeDislike();
            valoracion.setPost(postDao.findOne(id));
            valoracion.setUser(usuario);
            String value = request.queryParams("like");
            if(value.equals("Me gusta")){
                valoracion.setValoracion(true);
                System.out.println("entre");
            }
            else if(value.equals("No me gusta")){
                valoracion.setValoracion(false);
            }



            Post post = new Post();
            post = postDao.findOne(id);

            boolean check = false;
            for(LikeDislike val: post.getValoraciones()){
                if(val.getUser().getId()==valoracion.getUser().getId()){
                    val.setValoracion( valoracion.getValoracion());
                    check=true;
                    likeDislikeDao.update(val);
                    postDao.update(post);
                }

            }


            if(check==false){
                post.getValoraciones().add(valoracion);
                likeDislikeDao.add(valoracion);
                postDao.update(post);
            }

            response.redirect("/home");

            return "Ok";
        });

        post("/like/comentario/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            boolean autenticado=false;
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User usuario = new User();
            Comment comentario = new Comment();
            comentario = commentDao.findOne(id);
            Integer postId=0;

             usuario = usuarioDao.searchByUsername(request.cookie("username"));


            LikeDislike valoracion = new LikeDislike();
            valoracion.setComment(comentario);
            valoracion.setUser(usuario);

            String value = request.queryParams("like");
            if(value.equals("Me gusta")){
                valoracion.setValoracion(true);
            }
            else if(value.equals("No me gusta")){
                valoracion.setValoracion(false);
            }

            boolean check = false;
            for(LikeDislike val: comentario.getValoraciones()){
                if(val.getUser().getId()==valoracion.getUser().getId()){
                    val.setValoracion( valoracion.getValoracion());
                    check=true;
                    likeDislikeDao.update(val);
                    commentDao.update(comentario);
                }

            }


            if(check==false){
                comentario.getValoraciones().add(valoracion);
                likeDislikeDao.add(valoracion);
                commentDao.update(comentario);
            }




            response.redirect("/home");

            return "Ok";
        });

        post("/like/evento/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            boolean autenticado=false;
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User usuario = new User();

            usuario = usuarioDao.searchByUsername(request.cookie("username"));


            LikeDislike valoracion = new LikeDislike();
            valoracion.setEvent(eventDao.findOne(id));
            valoracion.setUser(usuario);
            String value = request.queryParams("like");
            if(value.equals("Me gusta")){
                valoracion.setValoracion(true);
                System.out.println("entre");
            }
            else if(value.equals("No me gusta")){
                valoracion.setValoracion(false);
            }



            Event event = new Event();
            event = eventDao.findOne(id);

            boolean check = false;
            for(LikeDislike val: event.getValoraciones()){
                if(val.getUser().getId()==valoracion.getUser().getId()){
                    val.setValoracion( valoracion.getValoracion());
                    check=true;
                    likeDislikeDao.update(val);
                    eventDao.update(event);
                }

            }


            if(check==false){
                event.getValoraciones().add(valoracion);
                likeDislikeDao.add(valoracion);
                eventDao.update(event);
            }

            response.redirect("/home");

            return "Ok";
        });


        post("/like/foto/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            boolean autenticado=false;
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User usuario = new User();

            usuario = usuarioDao.searchByUsername(request.cookie("username"));


            LikeDislike valoracion = new LikeDislike();
            valoracion.setPhoto(photoDao.findOne(id));
            valoracion.setUser(usuario);
            String value = request.queryParams("like");
            if(value.equals("Me gusta")){
                valoracion.setValoracion(true);
                System.out.println("entre");
            }
            else if(value.equals("No me gusta")){
                valoracion.setValoracion(false);
            }



            Photo event = new Photo();
            event = photoDao.findOne(id);

            boolean check = false;
            for(LikeDislike val: event.getValoraciones()){
                if(val.getUser().getId()==valoracion.getUser().getId()){
                    val.setValoracion( valoracion.getValoracion());
                    check=true;
                    likeDislikeDao.update(val);
                    photoDao.update(event);
                }

            }


            if(check==false){
                event.getValoraciones().add(valoracion);
                likeDislikeDao.add(valoracion);
                photoDao.update(event);
            }

            response.redirect("/home");

            return "Ok";
        });


                post("/comentario/evento/:id", (request, response) -> {
                    if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

                    QueryParamsMap map = request.queryMap();
                    boolean autenticado=false;
                    Integer id = Integer.parseInt(request.params("id"));
                    Comment comment = new Comment();
                    User usuario = new User();
                    User log = usuarioDao.searchByUsername(request.cookie("username"));
                    Event evento = eventDao.findOne(id);
                    comment.setComentario(request.queryParams("muro"));
                    comment.setUser(log);
                    comment.setEvent(eventDao.findOne(id));
                    commentDao.add(comment);
                    response.redirect("/home");

                    return "Ok";
                });

        post("/comentario/foto/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            QueryParamsMap map = request.queryMap();
            boolean autenticado=false;
            Integer id = Integer.parseInt(request.params("id"));
            Comment comment = new Comment();
            User usuario = new User();
            User log = usuarioDao.searchByUsername(request.cookie("username"));
            Photo foto = photoDao.findOne(id);
            comment.setComentario(request.queryParams("muro"));
            comment.setUser(log);
            comment.setPhoto(foto);
            commentDao.add(comment);
            response.redirect("/home");

            return "Ok";
        });

        post("/comentario/post/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            QueryParamsMap map = request.queryMap();
            boolean autenticado=false;
            Integer id = Integer.parseInt(request.params("id"));
            Comment comment = new Comment();
            User usuario = new User();
            User log = usuarioDao.searchByUsername(request.cookie("username"));
            Post evento = postDao.findOne(id);
            comment.setComentario(request.queryParams("muro"));
            comment.setUser(log);
            comment.setPost(evento);
            commentDao.add(comment);
            response.redirect("/home");

            return "Ok";
        });

        post("/addPost/:user", "multipart/form-data", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            long maxFileSize = 100000000;
            long maxRequestSize = 100000000;
            int fileSizeThreshold = 1024;

            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                    uploadDir.getAbsolutePath(), maxFileSize, maxRequestSize, fileSizeThreshold);
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    multipartConfigElement);

            Part amigoste = request.raw().getPart("amigos[]");
            Part text = request.raw().getPart("muro");
            Part uploadedFile = request.raw().getPart("uf");
            if(!uploadedFile.getSubmittedFileName().isEmpty()) {
                String fName = request.raw().getPart("uf").getSubmittedFileName();

                java.nio.file.Path out = Paths.get(uploadDir.getCanonicalPath() + "/" + fName);
                InputStream in = uploadedFile.getInputStream();
                Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                uploadedFile.delete();

                multipartConfigElement = null;
                uploadedFile = null;

                BufferedImage imagen = null;
                File here = new File(".");

                String path = uploadDir.getCanonicalPath() + "/" + fName;
                System.out.println(path);

                try {
                    imagen = ImageIO.read(new File((path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream imagenb = new ByteArrayOutputStream();
                try {
                    ImageIO.write(imagen, "jpg", imagenb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boolean autenticado=false;
                Photo fpost = new Photo();
                fpost.setFoto(imagenb.toByteArray());
                Integer id;
                Post post = new Post();
                post.setPhoto(fpost);
                User usuario = new User();
                String user = request.params("user");
                usuario= usuarioDao.searchByUsername(user);
                User log = usuarioDao.searchByUsername(request.cookie("username"));
                String s = request.queryParams("amigos[]");
                Tag t = new Tag();
                User u = usuarioDao.searchByUsername(s);
                t.setUsers(u);
                Notification n = new Notification();
                n.setSeen(false);
                if(log.getUsername() == usuario.getUsername())
                    n.setNotificacion(log.getUsername()+" Te ha mencionado en una entrada suya");
                else n.setNotificacion(log.getUsername()+" Te ha mencionado en una entrada de "+usuario.getUsername());
                n.setUser(u);
                notificationDao.add(n);

                post.setFecha(LocalDate.now());
                post.setLikes(0);
                post.setTexto(IOUtils.toString(text.getInputStream()));
                post.setUser(log);
                post.setWall(usuario.getWall());
                post.setEtiqueta(t);

                tagDao.add(t);
////
//
                  photoDao.add(fpost);
                postDao.add(post);

                response.redirect("/profile/" + user);

            }
            else {


                QueryParamsMap map = request.queryMap();
                boolean autenticado = false;
                Integer id;
                Post post = new Post();
                User usuario = new User();
                String user = request.params("user");
                usuario = usuarioDao.searchByUsername(user);
                User log = usuarioDao.searchByUsername(request.cookie("username"));
                String s = request.queryParams("amigos[]");

                Tag t = new Tag();
                User u = usuarioDao.searchByUsername(s);
                    t.setUsers(u);
                Notification n = new Notification();
                n.setSeen(false);
                if(log.getUsername() == usuario.getUsername())
                n.setNotificacion(log.getUsername()+" Te ha mencionado en una entrada suya");
                else n.setNotificacion(log.getUsername()+" Te ha mencionado en una entrada de "+usuario.getUsername());
                n.setUser(u);
                notificationDao.add(n);

                post.setFecha(LocalDate.now());
                post.setLikes(0);
                post.setTexto(IOUtils.toString(text.getInputStream()));
                post.setUser(log);
                post.setWall(usuario.getWall());
                post.setEtiqueta(t);

                tagDao.add(t);
                postDao.add(post);


                response.redirect("/profile/" + user);


            }
            return "Ok";
        });

        get("/post/borrar/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            Integer idpost = Integer.parseInt(request.params("id"));

            Post post = postDao.findOne(idpost);
            String s = post.getWall().getUser().getUsername();

            if (post != null){
                postDao.deleteById(post);
            }

            response.redirect("/profile/"+s);

            return null;

        },freeMarkerEngine);



        get("/comentario/borrar/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            Integer idpost = Integer.parseInt(request.params("id"));

            Comment post = commentDao.findOne(idpost);
            String s = post.getPost().getWall().getUser().getUsername();

            if (post != null){
                commentDao.deleteById(post);
            }

            response.redirect("/profile/"+s);

            return null;

        },freeMarkerEngine);

        get("/eliminarAlbum/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            Integer idpost = Integer.parseInt(request.params("id"));

            Album post = albumDao.findOne(idpost);
            String s = post.getUser().getUsername();

            if (post != null){
                albumDao.deleteById(post);
            }

            response.redirect("/profile/"+s);

            return null;

        },freeMarkerEngine);

        get("/eliminarFoto/:id", (request, response) -> {
            if(usuarioDao.searchByUsername(request.cookie("username"))==null)response.redirect("/");

            Integer idpost = Integer.parseInt(request.params("id"));

            Photo post = photoDao.findOne(idpost);
            String s = post.getAlbums().getUser().getUsername();

            if (post != null){
                photoDao.deleteById(post);
            }

            response.redirect("/profile/"+s);

            return null;

        },freeMarkerEngine);


    }
}
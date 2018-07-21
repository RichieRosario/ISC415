package Rutas;

import dao.*;
import hibernate.HibernateUtil;
import modelo.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Session;
import java.util.*;
import java.text.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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



        //Rutas Inicio
        get("/home", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            User user = new User();
            if(request.cookie("username")!=null) {
                user = usuarioDao.searchByUsername(request.cookie("username").toString());

                attributes.put("usuario", user);
                attributes.put("perfil", user.getUsername());
                attributes.put("admin", user.isAdministrator());
            }
            else{
                response.redirect("/");
            }
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        //Rutas amigos
        get("/friends", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.findOne(1); //prueba

            List<Integer> friendlist = friendshipDao.getAllFriends(user);

            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("friends", friendlist);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);

            return new ModelAndView(attributes, "friends.ftl");
        }, freeMarkerEngine);

        get("/friendRquests", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.findOne(1); //prueba

            List<Integer> friendRequests = friendshipDao.getFriendRequests(user);
            List<User> solicitudes = usuarioDao.getUsersById(friendRequests);

            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("solicitudes", solicitudes);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);

            return new ModelAndView(attributes, "friendRequests.ftl");
        }, freeMarkerEngine);
        get("/pendingRequests", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.findOne(1); //prueba

            List<Integer> pendingRequests = friendshipDao.getPendingRequests(user);
            List<User> solicitudesPendientes = usuarioDao.getUsersById(pendingRequests);

            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("solicitudesPendientes", solicitudesPendientes);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);

            return new ModelAndView(attributes, "pendingRequests.ftl");
        }, freeMarkerEngine);

        post("/sendRequest", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.findOne(1); //prueba

           String userId = request.params("userId");
           friendshipDao.sendFriendRequest(user, Integer.parseInt(userId));

           response.redirect("/");
           return null;
        });
        post("/acceptRequest", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.findOne(1); //prueba

            String userId = request.params("userId");
            friendshipDao.acceptRequest(user, Integer.parseInt(userId));
            response.redirect("/");
            return null;
        });
        post("/unFriend", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.findOne(1); //prueba

            String userId = request.params("userId");
            friendshipDao.unFriend(user, Integer.parseInt(userId));
            response.redirect("/");
            return null;
        });

        //Rutas profile
        get("/profile/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            String username = request.params("username");
            User user = usuarioDao.searchByUsername(username);
            User usuarioLogueado = usuarioDao.findOne(1);
            boolean isFriend = friendshipDao.checkIfFriend(usuarioLogueado, user.getId());

            List<Post> posts = postDao.getMyPosts(user.getId());
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("user", user);
            attributes.put("posts", posts);
            attributes.put("usuarioLogueado", usuarioLogueado);
            attributes.put("isFriend", isFriend);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);

            return new ModelAndView(attributes, "profile.ftl");
        }, freeMarkerEngine);

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
            Map<String, Object> attributes = new HashMap<>();
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean administrator = false;


            if(request.cookie("username")!=null) {
                autenticado = true;
                User user = usuarioDao.searchByUsername(request.cookie("username"));

                administrator = user.isAdministrator();


                attributes.put("usuarios", usuarioDao.getAll());
                attributes.put("perfil", user.getUsername());
                attributes.put("admin", administrator);
            }
            else{
                response.redirect("/");
            }

            return new ModelAndView(attributes, "gestionarUsuarios.ftl");
        }, freeMarkerEngine);

        //Rutas Registrarse
        post("/registrarse", (request, response) -> {

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
            Date date = format.parse(request.queryParams("date"));
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
            profileDao.add(newprofile);

            response.redirect("/");

            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);
    }
}
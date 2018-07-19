package Rutas;

import dao.*;
import hibernate.HibernateUtil;
import modelo.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Session;

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
            user = usuarioDao.findOne(1); //prueba

            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            List<User> posiblesConocidos = user.usersMayKnow(user.getId());

            List<Post> posts = postDao.getFriendPosts(user, friendlist);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            attributes.put("user", user);
            attributes.put("posts", posts);
            attributes.put("amigos", amigos);
            attributes.put("posiblesConocidos", posiblesConocidos);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);

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
                autenticado=true;
                user = usuarioDao.searchByUsername(request.cookie("username"));

                administrator = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){
                user = usuarioDao.searchByUsername(request.session().attribute("username"));
                autenticado = true;
                administrator = user.isAdministrator();

            }


            if(autenticado == true) {
                administrator = user.isAdministrator();
                String nombre = usuarioDao.getProfile(user.getId()).getNombre() + " " + usuarioDao.getProfile(user.getId()).getApellido();
                attributes.put("administrator", administrator);
                attributes.put("nombreusuario", nombre);
                attributes.put("autenticado", autenticado);
            }


            else {
                attributes.put("usuariodentro", "Huesped");
                attributes.put("admininistrator", false);
                attributes.put("autenticado", false);
            }


            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        //Rutas Login
        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            boolean autenticado = false;
            boolean administrator = false;

            if(request.cookie("username")!=null)
            {
                autenticado=true;
                User user = usuarioDao.searchByUsername(request.cookie("username"));
                administrator = user.isAdministrator();
            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                User user = usuarioDao.searchByUsername(request.session().attribute("username"));

                administrator = user.isAdministrator();
            }


            if(autenticado == true) {

                response.redirect("/");}


            else {
                attributes.put("administrator", false);
                attributes.put("autenticado", false);
            }

            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);

        post("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String username = request.queryParams("username") != null ? request.queryParams("username") : "";
            String password = request.queryParams("password") != null ? request.queryParams("password") : "";
            String remember = request.queryParams("remember") != null ? request.queryParams("remember") : "";
            User user = usuarioDao.searchByUsername(username);

            if( user.getUsername().equals(username) && user.getPassword().equals(password)){
                QueryParamsMap map2 = request.queryMap();
                Session session = request.session();
                session.attribute("username", map2.get("username").value());
                session.maxInactiveInterval(900);

                if(remember.equals("remember")){
                    response.cookie("username",map2.get("username").value(), 604800);
                    request.cookie("username");

                }
                String nombre = usuarioDao.getProfile(user.getId()).getNombre() + " " + usuarioDao.getProfile(user.getId()).getApellido();

                attributes.put("nombreusuario", nombre);
                attributes.put("administrator", user.isAdministrator());
                attributes.put("autenticado", true);

                response.redirect("/");
            }

            else{
                attributes.put("usuariodentro","Huesped");
                attributes.put("autor", false);
                attributes.put("administrator", false);
                attributes.put("autenticado", false); }

            return new ModelAndView(attributes, "login.ftl");
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


            if(request.cookie("username")!=null)
            {autenticado=true;
                User user = usuarioDao.searchByUsername(request.cookie("username"));

                administrator = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                User user = usuarioDao.searchByUsername(request.session().attribute("username"));

                administrator = user.isAdministrator();
            }

            attributes.put("usuarios", usuarioDao.getAll());
            attributes.put("autenticado", autenticado);
            attributes.put("administrator", administrator);


            return new ModelAndView(attributes, "gestionarUsuario.ftl");
        }, freeMarkerEngine);

        //Rutas Registrarse
        get("/registrarse", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();

            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean administrator = false;

            if(request.cookie("username")!=null)
            {autenticado = true;
                User user = usuarioDao.searchByUsername(request.cookie("username"));

                administrator = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                User user = usuarioDao.searchByUsername(request.session().attribute("username"));

                administrator = user.isAdministrator();
            }

            attributes.put("administrator", administrator);
            attributes.put("autenticado", autenticado);

            return new ModelAndView(attributes, "crearUsuario.ftl");
        }, freeMarkerEngine);
//        post("/registrarse", (request, response) -> {
//
//            QueryParamsMap map = request.queryMap();
//
//            User usuario = new User();
//            usuario.setUsername(map.get("username").value());
//            usuario.setEmail(map.get("email").value());
//            usuario.setPassword(map.get("password").value());
//            if(request.queryParams("rol")!=null){
//                if(request.queryParams("rol").equals( "administrator")){
//
//                    usuario.setAdministrator(true);
//                }
//
//            else{
//                usuario.setAdministrator(false);
//            }}
//
//            UserDaoImpl userDao = null;
//
//            if(userDao.searchByUsername(usuario.getUsername())==null){
//
//                userDao.add(usuario);
//
//                response.redirect("/");
//
//                return null;
//            }
//            else {
//                return "Usuario ya existe!";
//            }
//
//        });
    }
}
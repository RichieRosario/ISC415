package Rutas;

import dao.*;
import hibernate.HibernateUtil;
import javafx.geometry.Pos;
import modelo.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.Cookie;
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
        get("/usuarios/editar/:id", (request, response) -> {
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;


            if(request.cookie("username")!=null)
            {autenticado=true;
                User user = usuarioDao.searchByUsername(request.cookie("username"));

                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                User user = usuarioDao.searchByUsername(request.session().attribute("username"));
                admin = user.isAdministrator();
            }

            Integer idusuario = Integer.parseInt(request.params("id"));

            User usuario = usuarioDao.findOne(idusuario);


            Map<String, Object> attributes = new HashMap<>();

            attributes.put("autenticado", autenticado);
            attributes.put("admin", admin);
            attributes.put("idusuario", idusuario.toString());
            attributes.put("usuario", usuario);

            return new ModelAndView(attributes, "modificarUsuario.ftl");
        }, freeMarkerEngine);
        post("/usuarios/editar/:id", (request, response) -> {

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Integer idusuario = Integer.parseInt(request.params("id"));
            String username = request.queryParams("username");
            String nombre = request.queryParams("nombre");
            String password = request.queryParams("password");
            Boolean administrator = Boolean.parseBoolean(request.queryParams("administrator"));
            String email = request.queryParams("email");
            String apellido = request.queryParams("apellido");
            String ciudadactual = request.queryParams("ciudadactua");
            String lugartrabajo = request.queryParams("lugartrabajo");
            java.util.Date fechanacimiento = format.parse(request.queryParams("fechanacimiento"));
            String lugarestudio = request.queryParams("lugarestudio");
            String lugarnacimiento = request.queryParams("lugarnacimiento");
            Character sexo = request.queryParams("sexo").charAt(0);
            User usuario = new User(idusuario, username, nombre, password, administrator, null, null, null);
            Profile profile = usuarioDao.getProfile(idusuario);
            profile = new Profile(profile.getId(), nombre, apellido,fechanacimiento, lugarnacimiento, ciudadactual, lugarestudio, lugartrabajo, sexo);
            profileDao.update(profile);
            usuarioDao.update(usuario);

            response.redirect("/usuarios");

            return null;
        });
        get("/usuarios/borrar/:id", (request, response) -> {


            Integer idusuario = Integer.parseInt(request.params("id"));

            User usuario = usuarioDao.findOne(idusuario);
            if (usuario != null){
                usuarioDao.deleteById(usuario);
            }

            response.redirect("/usuarios");

            return null;

        },freeMarkerEngine);

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
        post("/registrarse", (request, response) -> {

            QueryParamsMap map = request.queryMap();
            SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
            User usuario = new User();
            Profile profile = new Profile();
            usuario.setUsername(map.get("username").value());
            usuario.setEmail(map.get("email").value());
            usuario.setPassword(map.get("password").value());
            usuarioDao.getProfile(usuario.getId()).setNombre(map.get("nombre").value());
            usuarioDao.getProfile(usuario.getId()).setApellido(map.get("apellido").value());
            usuarioDao.getProfile(usuario.getId()).setCiudadactual(map.get("ciudad").value());
            java.util.Date fechanacimiento = format.parse(request.queryParams("fechanacimiento"));
            usuarioDao.getProfile(usuario.getId()).setLugarestudio(map.get("lugarestudio").value());
            usuarioDao.getProfile(usuario.getId()).setLugartrabajo(map.get("lugartrabajo").value());
          //  usuarioDao.getProfile(usuario.getId()).setSexo(map.get("sexo").value());
            if(request.queryParams("rol")!=null){
                if(request.queryParams("rol").equals( "administrator")){

                    usuario.setAdministrator(true);
                }

            else{
                usuario.setAdministrator(false);
            }}

            UserDaoImpl userDao = null;

            if(userDao.searchByUsername(usuario.getUsername())==null){

                userDao.add(usuario);
                profileDao.add(userDao.getProfile(usuario.getId()));
                response.redirect("/");

                return null;
            }
            else {
                return "Usuario ya existe!";
            }

        });

        //Rutas Likes
        post("/like/post/:id", (request, response) -> {
            boolean autenticado=false;
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User usuario = new User();
            if(request.cookie("username")!=null)
            {autenticado=true;
                usuario = usuarioDao.searchByUsername(request.cookie("username"));


            }
            else if(usuarioDao.searchByUsername(request.session().attribute("username"))!=null && request.cookie("username")==null){
                autenticado=true;
                usuario = usuarioDao.searchByUsername(request.session().attribute("username"));}

            LikeDislike valoracion = new LikeDislike();
            valoracion.setPost(postDao.findOne(id));
            valoracion.setUser(usuario);
            String value = request.queryParams("like");
            if(value.equals("Me divierte")){
                valoracion.setValoracion(true);
            }
            else if(value.equals("Me aborrece")){
                valoracion.setValoracion(false);
            }



            PostDaoImpl postDao1 = null;
            Post post = new Post();
            post = postDao1.findOne(id);

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
            boolean autenticado=false;
            QueryParamsMap map = request.queryMap();
            Integer id = Integer.parseInt(request.params("id"));
            User usuario = new User();
            Comment comentario = commentDao.findOne(id);
            Integer postId=0;

            if(request.cookie("username")!=null)
            {autenticado=true;
                usuario = usuarioDao.searchByUsername(request.cookie("username"));

            }
            else if(usuarioDao.searchByUsername(request.session().attribute("username"))!=null && request.cookie("username")==null){
                autenticado=true;
                usuario = usuarioDao.searchByUsername(request.session().attribute("username"));}

            LikeDislike valoracion = new LikeDislike();
            valoracion.setComment(comentario);
            valoracion.setUser(usuario);
            LikeDislikeDao valoracionDao = null;

            String value = request.queryParams("like");
            if(value.equals("Me divierte")){
                valoracion.setValoracion(true);
            }
            else if(value.equals("Me aborrece")){
                valoracion.setValoracion(false);
            }

            boolean check = false;
            for(LikeDislike val: comentario.getValoraciones()){
                if(val.getUser().getId()==valoracion.getUser().getId()){
                    val.setValoracion( valoracion.getValoracion());
                    check=true;
                    valoracionDao.update(val);
                    commentDao.update(comentario);
                }

            }


            if(check==false){
                comentario.getValoraciones().add(valoracion);
                valoracionDao.add(valoracion);
                commentDao.update(comentario);
            }


            for(Post post:postDao.getAll()){
                for(Comment comen:post.getComments()){
                    if(comen.getId() == comentario.getId()){
                        postId=post.getId();
                    }
                }
            }


            response.redirect("/home");

            return "Ok";
        });

        //Rutas Posts
        post("/addPost", (request, response) -> {

            QueryParamsMap map = request.queryMap();
            boolean autenticado=false;
            Integer id;
            Post post = new Post();
            User usuario = new User();

            if(request.cookie("username")!=null)
            {autenticado=true;
                usuario = usuarioDao.searchByUsername(request.cookie("username"));


            }
            else if(usuarioDao.searchByUsername(request.session().attribute("username"))!=null && request.cookie("username")==null){
                autenticado=true;
                usuario = usuarioDao.searchByUsername(request.session().attribute("username"));}

            post.setFecha(LocalDate.now());
            post.setLikes(0);
            post.setTexto(map.get("texto").value());
            post.setUser(usuario);
            post.setWall(wallDao.findWallByUser(usuario.getId()));
            String etiquetas = (map.get("etiqueta").value());

            List<Tag> etiq = new ArrayList<Tag>();

            String[] ets = etiquetas.split(",");

            //   Long size2 = Long.parseLong(String.valueOf(size));

            Set<Tag> etiqs = new HashSet<>();

            for(String etiquet : etiquetas.split(",")){

                Tag etiqueta = new Tag();
                User user = usuarioDao.searchByUsername(etiquet);
                etiqueta = tagDao.searchByTag(etiquet);
                etiqs.add(new Tag(user));
            }


            post.setEtiquetas(etiqs);
            postDao.add(post);
            response.redirect("/home");

            return "Ok";
        });
        get("/post/editar/:id", (request, response) -> {

            Integer idpost = Integer.parseInt(request.params("id"));
            boolean autenticado = Boolean.parseBoolean(request.queryParams("autenticado"));
            boolean admin=false;

            if(request.cookie("username")!=null)
            {autenticado=true;
                User user = usuarioDao.searchByUsername(request.cookie("username"));

                admin = user.isAdministrator();

            }
            else if(request.session().attribute("username") != null && request.cookie("username")==null){

                autenticado = true;
                User user = usuarioDao.searchByUsername(request.session().attribute("username"));

                admin = user.isAdministrator();
            }

            Post post = postDao.findOne(idpost);
            String etiquetastemp="";
            for(Tag et : post.getEtiquetas()){
                etiquetastemp+=et.getToUser().getUsername()+',';
            }
            String etiquetasfini="";
            for(int i=0;i<etiquetastemp.length()-1;i++){
                etiquetasfini+=etiquetastemp.charAt(i);
            }

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("idpost", post.getId());
            attributes.put("autenticado", autenticado);
            attributes.put("admin", admin);
            attributes.put("posts", post);
            attributes.put("etiquetas",etiquetasfini);

            return new ModelAndView(attributes, "modificarPost.ftl");
        }, freeMarkerEngine);
        post("/post/editar/:id", (request, response) -> {

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Integer idpost = Integer.parseInt(request.params("id"));
            String texto = request.queryParams("texto");
            LocalDate fecha = LocalDate.parse(request.queryParams("fecha"));
            int likes = postDao.findOne(idpost).getLikes();
            String etiquetas = request.queryParams("etiqueta");

            List<Tag> etiq = new ArrayList<Tag>();

            String[] ets = etiquetas.split(",");

            Set<Tag> etiqs = new HashSet<>();

            for(String etiquet : etiquetas.split(",")){

                Tag etiqueta = new Tag();
                etiqueta = tagDao.searchByTag(etiquet);
                etiqs.add(new Tag(usuarioDao.searchByUsername(etiquet)));
            }


            Post post = new Post(idpost, texto, fecha, likes, null, null, postDao.findOne(idpost).getUser(),
                    wallDao.findWallByUser(postDao.findOne(idpost).getUser().getId()), null, null);

            post.setEtiquetas(etiqs);
            post.setValoraciones(postDao.findOne(idpost).getValoraciones());

            Set<Comment>comentariostemp= postDao.findOne(idpost).getComments();
            post.setComments(comentariostemp);

            postDao.update(post);

            response.redirect("/home");

            return null;
        });
        get("/post/borrar/:id", (request, response) -> {

            Integer idpost = Integer.parseInt(request.params("id"));

            Post post = postDao.findOne(idpost);

            if (post != null){
                postDao.deleteById(post);
            }

            response.redirect("/home");

            return null;

        },freeMarkerEngine);



    }
}
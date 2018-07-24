package Rutas;

import dao.*;
import hibernate.HibernateUtil;
import javafx.geometry.Pos;
import modelo.*;
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
            Friendship f = new Friendship();
            f.setFromUser(1);
            f.setToUser(2);
            friendshipDao.add(f);

            List<Integer> friendlist = friendshipDao.getAllFriends(user);
            List<User> amigos = usuarioDao.getUsersById(friendlist);
            ArrayList<Profile> profilesList = new ArrayList<>();

            if(amigos.size() > 0)
            {
                for(User user1 : amigos){
                    Profile profile = usuarioDao.getProfile(user1);
                    profilesList.add(profile);
                }
            }


            List<Notification> notificationList = notificationDao.unseenNotifications(user);


            attributes.put("usuario", user);
            attributes.put("perfil", user.getUsername());
            attributes.put("admin", user.isAdministrator());
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("amigos", amigos);
            attributes.put("perfiles", profilesList);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);

            return new ModelAndView(attributes, "friends.ftl");
        }, freeMarkerEngine);

        get("/friendRequests", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();
            User user = new User();
            user = usuarioDao.findOne(2); //prueba

            List<Integer> friendRequests = friendshipDao.getFriendRequests(user);
            List<User> solicitudes = usuarioDao.getUsersById(friendRequests);

            ArrayList<Profile> profilesList = new ArrayList<>();

            List<Notification> notificationList = notificationDao.unseenNotifications(user);
            attributes.put("user", user);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("solicitudes", solicitudes);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);

            if(friendRequests.size() > 0)
            {
                List<User> personList = usuarioDao.getUsersById(friendRequests);

                for(User user1 : personList){
                    Profile profile = usuarioDao.getProfile(user1);
                    profilesList.add(profile);
                }
                attributes.put("profilesList", profilesList);
            }


            return new ModelAndView(attributes, "friendRequests.ftl");
        }, freeMarkerEngine);

        get("/pendingRequests", (request, response) -> {
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

            List<Notification> notificationList = notificationDao.unseenNotifications(user);
            attributes.put("user", user);
            attributes.put("profile", usuarioDao.getProfile(user));
            attributes.put("solicitudesPendientes", solicitudesPendientes);
            attributes.put("numeroNotificaciones", notificationList.size());
            attributes.put("notificaciones", notificationList);


                List<User> personList = new ArrayList<User>();
                personList = usuarioDao.getUsersById(pendingRequests);
                attributes.put("personList", personList);

            return new ModelAndView(attributes, "pendingRequests.ftl");
        }, freeMarkerEngine);

        post("/sendRequest/:userId", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            QueryParamsMap map = request.queryMap();

            User user = new User();
            user = usuarioDao.searchByUsername(request.cookie("username")); //prueba

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
                // List<Notification> notificationList = notificationDao.unseenNotifications(user);

                attributes.put("user", user);
                attributes.put("posts", posts);
                attributes.put("isFriend", isFriend);
            List<Notification> notificationList = notificationDao.unseenNotifications(user);

            List<Integer> friendsids = friendshipDao.getAllFriends(usuarioLogueado);
            List<User> friends = usuarioDao.getUsersById(friendsids);
            friends = friends.subList(Math.max(friends.size() - 5, 0), friends.size());
            List<Profile> friendsProfiles = new ArrayList<>();

            for(User user1 : friends){
                friendsProfiles.add(usuarioDao.getProfile(user1));
            }

            Wall muro = user.getWall();
            attributes.put("muroeventos", muro.getEvents());
            attributes.put("muroentradas", muro.getPosts());
            attributes.put("eventoscomentarios", muro.getEvents());
            attributes.put("totalFriends", friendsids.size());
            attributes.put("friendsProfiles", friendsProfiles);
                //attributes.put("numeroNotificaciones", notificationList.size());
                //attributes.put("notificaciones", notificationList);
            }

            return new ModelAndView(attributes, "profile.ftl");
        }, freeMarkerEngine);

        post("/subirfoto","multipart/form-data",  (request, response) -> {


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

                    attributes.put("usuario", user);


                attributes.put("usuarios", usuarioDao.getAll());
                attributes.put("perfil", user.getUsername());
                attributes.put("admin", administrator);
            }
            else{
                response.redirect("/");
            }

            return new ModelAndView(attributes, "gestionarUsuarios.ftl");
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
            Profile profile = usuarioDao.getProfile(usuario);
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

        //Rutas Likes
        post("/like/post/:id", (request, response) -> {
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
            LikeDislikeDao valoracionDao = null;

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

        post("/like/evento/:id", (request, response) -> {
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

        //Rutas Posts
        post("/addPost/:user", (request, response) -> {

            QueryParamsMap map = request.queryMap();
            boolean autenticado=false;
            Integer id;
            Post post = new Post();
            User usuario = new User();
            String user = request.params("user");
            usuario= usuarioDao.searchByUsername(user);
            User log = usuarioDao.searchByUsername(request.cookie("username"));

            post.setFecha(LocalDate.now());
            post.setLikes(0);
            post.setTexto(map.get("muro").value());
            post.setUser(log);
            post.setWall(usuario.getWall());
            String etiquetas = (map.get("etiqueta").value());

            List<Tag> etiq = new ArrayList<Tag>();


            //   Long size2 = Long.parseLong(String.valueOf(size));

            post.setEtiquetas(null);
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
                    wallDao.findWallByUser(postDao.findOne(idpost).getUser()), null, null);

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
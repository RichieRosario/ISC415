package main;

import dao.Sql2oUsuarioDao;
import encapsulacion.Usuario;
import freemarker.template.Configuration;
import hibernate.HibernateUtil;
import servicios.*;

import Rutas.RutasWeb;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Version;
import java.util.HashMap;
import java.util.Map;
import j2html.TagCreator;
import j2html.tags.ContainerTag;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.*;

import static j2html.TagCreator.*;


import websocket.*;


public class Main {
    public static Map<String, Session> usuariosConectados = new HashMap<>();


    public static void main(String[] args) throws Exception {

        Hash hash = null;
        ConnectionService.startDb();
        final Configuration configuration = new Configuration(new Version(2, 3, 23));

        configuration.setClassForTemplateLoading(Main.class, "/templates");

        staticFileLocation("/public/");

        webSocket("/mensajeServidor", ServidorMensajesWebSocketHandler.class);
        init();

        HibernateUtil.buildSessionFactory().openSession().close();

        Sql2oUsuarioDao usuarioadmin = new Sql2oUsuarioDao(Usuario.class);
        if(usuarioadmin.searchByUsername("admin") == null){
        Usuario usuarioPorDefecto = new Usuario(1L, "admin", "Administrador", Hash.sha1("admin"), true, false);
        usuarioadmin.add(usuarioPorDefecto);}

        HibernateUtil.openSession().close();

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        new RutasWeb(freeMarkerEngine);


    }
}
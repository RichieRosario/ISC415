package websocket;


import com.google.gson.Gson;
import encapsulacion.Chat;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import main.Main;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static j2html.TagCreator.p;

@WebSocket
public class ServidorMensajesWebSocketHandler {

    /**
     * Una vez conectado el cliente se activa este metodo.
     * @param usuario
     */
    @OnWebSocketConnect
    public void conectando(Session usuario){

    }

    /**
     * Una vez cerrado la conexión, es ejecutado el metodo anotado
     * @param usuario
     * @param statusCode
     * @param reason
     */
    @OnWebSocketClose
    public void cerrandoConexion(Session usuario, int statusCode, String reason) {
        for(Iterator<Map.Entry<String, Session>> it = Main.usuariosConectados.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Session> entry = it.next();
            if(entry.getValue().equals(usuario)) {
                it.remove();
            }
        }
    }

    /**
     * Una vez recibido un mensaje es llamado el metodo anotado.
     * @param usuario
     * @param message
     */
    @OnWebSocketMessage
    public void recibiendoMensaje(Session usuario, String message) throws IOException {
        Gson gson = new Gson();
        Chat mc = gson.fromJson(message, Chat.class);

        if (mc.isInicializando()){
            Main.usuariosConectados.put(mc.getUsuario_origen(),usuario);
            return;
        }
        Session session = Main.usuariosConectados.get(mc.getUsuario_destino());
        if( session != null)
            session.getRemote().sendString(gson.toJson(mc));
        else{
            mc = new Chat("Server","El usuario se ha desconectado","",false,"Servidor");
            usuario.getRemote().sendString(gson.toJson(mc));
        }


//        for (Map.Entry<String, Session> entry : main.Main.usuariosConectados.entrySet())
//        {
//            if(mc.getUsuario_destino().equals(entry.getKey())){
//                System.out.println(entry.getKey());
//                entry.getValue().getRemote().sendString(gson.toJson(mc));
//            }
//
//        }

    }



}

package servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by vacax on 27/05/16.
 */
public class ConnectionService {

    /**
     *
     * @throws SQLException
     */
    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    /**
     *
     * @throws SQLException
     */
    public static void stopDb() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }


    /**
     * Metodo para recrear las tablas necesarios
     * @throws SQLException
     */
    public static void crearTablas() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n" +
                "\n" +
                "  username      VARCHAR2(255),\n" +
                "  nombre        VARCHAR2(255),\n" +
                "  password      VARCHAR2(255),\n" +
                "  administrator BOOLEAN,\n" +
                "  autor         BOOLEAN\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS etiquetas (\n" +
                "  id       BIGINT PRIMARY KEY,\n" +
                "  etiqueta VARCHAR2(255)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS articulos (\n" +
                "  id       BIGINT PRIMARY KEY,\n" +
                "  titulo VARCHAR2(255),\n" +
                "  cuerpo   VARCHAR2(255),\n" +
                "  autor_id BIGINT REFERENCES usuarios(id) ON UPDATE CASCADE,\n" +
                "  fecha    DATE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS comentarios (\n" +
                "  id          BIGINT PRIMARY KEY,\n" +
                "  comentario VARCHAR2(255),\n" +
                "  autor_id    BIGINT REFERENCES usurios(id) ON UPDATE CASCADE,\n" +
                "  ARTICULO_ID BIGINT REFERENCES articulos(id) ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS articulos_comentarios (\n" +
                "  articulo_id  BIGINT REFERENCES articulos(id) ON UPDATE CASCADE,\n" +
                "  comentario_id BIGINT REFERENCES comentarios (id) ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS articulos_etiquetas (\n" +
                "  articulo_id BIGINT REFERENCES articulos(id) ON UPDATE CASCADE,\n" +
                "  etiqueta_id BIGINT REFERENCES etiquetas(id) ON UPDATE CASCADE\n" +
                ");";
        Connection con = DBService.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

}
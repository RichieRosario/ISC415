package servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnectionService {


    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    public static void stopDb() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }



    public static void crearTablas() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n" +
                "\n" +
                "  id            BIGINT auto_increment PRIMARY KEY,\n"+
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
                "  id       BIGINT auto_increment PRIMARY KEY,\n" +
                "  titulo VARCHAR2(255),\n" +
                "  cuerpo   VARCHAR2(MAX),\n" +
                "  autorID BIGINT REFERENCES usuarios(id) ON UPDATE CASCADE,\n" +
                "  fecha    DATE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS comentarios (\n" +
                "  id          BIGINT auto_increment PRIMARY KEY,\n" +
                "  comentario VARCHAR2(255),\n" +
                "  autorId    BIGINT REFERENCES usuarios(id) ON UPDATE CASCADE,\n" +
                "  articuloId BIGINT REFERENCES articulos(id) ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS articulos_comentarios (\n" +
                "  articuloId  BIGINT REFERENCES articulos(id) ON UPDATE CASCADE,\n" +
                "  comentarioId BIGINT REFERENCES comentarios (id) ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS articulos_etiquetas (\n" +
                "  articuloId BIGINT REFERENCES articulos(id) ON UPDATE CASCADE,\n" +
                "  etiquetaId BIGINT REFERENCES etiquetas(id) ON UPDATE CASCADE\n" +
                ");";
        Connection con = DBService.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

}
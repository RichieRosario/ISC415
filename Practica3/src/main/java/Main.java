
import servicios.*;
import java.sql.SQLException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {

        ConnectionService.startDb();

        DBService.getInstancia().testConexion();

        ConnectionService.stopDb();
    }
}
package servicios;

import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.Scanner;

public class H2Server {

    public static void main(String[] args) throws SQLException {
        Server server = Server.createTcpServer(args);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite opcion para el servidor: [0 = parar] & [1 = iniciar]");
        int opt = scanner.nextInt();

        while (true) {
            switch (opt) {
                case 0:
                    if (server.isRunning(true) == false) {
                    } else {
                        server.stop();
                        server.shutdown();
                        System.exit(1);
                          }
                    break;
                case 1:
                    if (server.isRunning(true) == false) {
                        server.start();
                           } else {
                          }
                    break;
                default:
                    break;
            }

            System.out.println("Digite opcion para el servidor: [0 = parar] & [1 = iniciar]");
            opt = scanner.nextInt();
             }
    }
}

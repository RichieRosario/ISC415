package servicios;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;

public class H2Server {
    private static final Logger logger = LoggerFactory.getLogger(H2Server.class);

    public static void main(String[] args) throws SQLException {
        Server server = Server.createTcpServer(args);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite opcion para el servidor: [0 = parar] & [1 = iniciar]");
        int opt = scanner.nextInt();
        logger.info("Opcion digitada: " + opt);

        while (true) {
            switch (opt) {
                case 0:
                    if (server.isRunning(true) == false) {
                        logger.warn("El servidor no se ha iniciado. Por tanto no se puede apagar. Porfavor inicielo.");
                    } else {
                        logger.info("Apagando y terminando servidor.");
                        server.stop();
                        server.shutdown();
                        logger.info("Servidor apagado y terminado.");
                        System.exit(1);
                        logger.info("Programa terminado.");
                    }
                    break;
                case 1:
                    if (server.isRunning(true) == false) {
                        logger.info("Iniciando servidor");
                        server.start();
                        logger.info("Servidor arriba...");
                    } else {
                        logger.warn("Servidor ya esta arriba...");
                    }
                    break;
                default:
                    logger.info("DEBE ELEGIR OPCION [0 - 1]");
                    break;
            }

            System.out.println("Digite opcion para el servidor: [0 = parar] & [1 = iniciar]");
            opt = scanner.nextInt();
            logger.info("Opcion digitada: " + opt);
        }
    }
}

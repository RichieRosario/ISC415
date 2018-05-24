import java.io.IOException;
import org.apache.commons.validator.*;
import java.util.*;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Main {


    public static void ItemA(Document d) {
        System.out.println("A - Cantidad de líneas del recurso retornado: \n");
        String lines[] = d.toString().split("\\n");
        System.out.println("El documento consultado tiene un total de " + lines.length + " líneas. \n");
    }

    public static void ItemB(Document d) {
        System.out.println("B - Cantidad de párrafos (p) que contiene el documento HTML: \n");
        Elements parrafos = d.getElementsByTag("p");
        System.out.println("El documento consultado tiene un total de " + parrafos.size() + " párrafos. \n");
    }

    public static void ItemC(Document d) {
        System.out.println("C - Cantidad de imágenes (img) dentro de los párrafos que contiene el archivo HTML: \n");
        Elements parrafos = d.getElementsByTag("p");
        int conta = 0;
        for (Element e : parrafos) {
            conta += e.getElementsByTag("img").size();

        }
        System.out.println("El documento consultado tiene un total de " + conta + " imágenes dentro de párrafos. \n");
    }

    public static void ItemD(Document d) {

        System.out.println("D - Cantidad de formularios (form) que contiene el HTML categorizando por el método implementado POST o GET: \n");
        Elements formsPOSThelper = d.getElementsByTag("form");
        int conta = 0;
        int conta2 = 0;
        for (Element e : formsPOSThelper) {
            if ((e.attributes().get("method")).contains("post")) {
                conta2++;
            } else if ((e.attributes().get("method")).contains("get")) {
                conta++;
            }
        }

        if (conta != 1) System.out.println("El documento consultado tiene un total de " + conta + " formularios GET.");
        else System.out.println("El documento consultado tiene un total de " + conta + " formulario GET.");
        if (conta2 != 1)
            System.out.println("El documento consultado tiene un total de " + conta2 + " formularios POST. \n");
        else System.out.println("El documento consultado tiene un total de " + conta2 + " formulario POST. \n");

    }

    public static void ItemE(Document d) {

        System.out.println("E - Campos y sus tipos, por cada Formulario: \n");
        Elements formsPOSThelper = d.getElementsByTag("form");
        int i = 1;
        int j = 1;
        for (Element e : formsPOSThelper) {
            System.out.println("Form #" + i);

            Elements inputs = e.getElementsByTag("input");
            for (Element e2 : inputs) {
                System.out.println("Campo #" + j + "\t Tipo: " + e2.attributes().get("type"));
                j++;
            }

            i++;
        }


    }

    public static void ItemF(Document d) {

        System.out.println("\nF - Respuesta de petición por cada Formulario POST, con un header y un parametro: \n");
        Elements formsPOSThelper = d.getElementsByTag("form");
        int i = 1;
        int j = 1;
        for (Element e : formsPOSThelper) {
            System.out.println("Form #" + i);
            if ((e.attributes().get("method")).contains("post")) {
                try {
                    String abs = e.absUrl("action");
                    Document postDoc = Jsoup.connect(abs)
                            .data("asignatura", "practica1")
                            .header("matricula", "20110714")
                            .userAgent("Chrome")
                            .post();
                    System.out.println(postDoc.body());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
        i++;
    }


    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        boolean urlvalido = false;
        UrlValidator uv = new UrlValidator();

        System.out.print("Escriba la URL que desea acceder (Recuerde agregar http:// al inicio): ");
        String in = sc.next();
        if (uv.isValid(in)) {
            System.out.println("\n");
            System.out.println("Información Relevante \n");


            Document d = Jsoup.connect(in).get();
            ItemA(d);
            ItemB(d);
            ItemC(d);
            ItemD(d);
            ItemE(d);
            ItemF(d);
        } else {
            System.out.println("La URL digitada no fue encontrada.");
        }
    }

}





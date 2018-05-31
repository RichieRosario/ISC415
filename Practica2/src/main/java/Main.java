

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Main {
    public static List<Estudiante> est = new ArrayList<Estudiante>();

    public static void main(String[] args) {
        est.add(new Estudiante(10000000,"prueba","prueba","000-000-0000"));

        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");

        Spark.get("/", (request, response) -> {

            StringWriter writer = new StringWriter();
                  try {
                        Template formTemplate = configuration.getTemplate("templates/form.ftl");
                       Map<String, Object> map = new HashMap<>();
                        map.put("lista",est);

                        formTemplate.process(map, writer);
                    } catch (Exception e) {
                        Spark.halt(500);
                    }






            return writer;
        });

        Spark.post("/", (request, response) -> {
            StringWriter writer = new StringWriter();

            try {
                String nombre = request.queryParams("nombre") != null ? request.queryParams("nombre") : "";
                String apellido = request.queryParams("apellido") != null ? request.queryParams("apellido") : "";
                String matricula = request.queryParams("matricula") != null ? request.queryParams("matricula") : "";
                String telefono = request.queryParams("telefono") != null ? request.queryParams("telefono") : "";
                Template resultTemplate = configuration.getTemplate("templates/form.ftl");
                Estudiante temp = new Estudiante(Integer.parseInt(matricula),nombre,apellido,telefono);

                est.add(temp);
                Map<String, Object> map = new HashMap<>();
                map.put("matricula", matricula);
                map.put("nombre", nombre);
                map.put("apellido", apellido);
                map.put("telefono",telefono);
                map.put("lista",est);

                resultTemplate.process(map, writer);


            } catch (Exception e) {
                Spark.halt(500);
            }

            return writer;
        });

        Spark.get("/modificar", (request, response) -> {

            StringWriter writer = new StringWriter();
            try {
              String indice = request.queryParams("indice");
              int idx = Integer.parseInt(indice);
              Estudiante student = est.get(idx);

                Template editarTemplate = configuration.getTemplate("templates/modificar.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("matricula", student.obtenerMatricula());
                map.put("nombre", student.obtenerNombre());
                map.put("apellido", student.obtenerApellido());
                map.put("telefono",student.obtenerTelefono());
                map.put("indice",indice);

                map.put("lista",est);

                editarTemplate.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }






            return writer;
        });

        Spark.post("/modificar", (request, response) -> {

            StringWriter writer = new StringWriter();
            try {
                String indice = request.queryParams("indice");
                int idx = Integer.parseInt(indice);
                String nombre = request.queryParams("nombre") != null ? request.queryParams("nombre") : "";
                String apellido = request.queryParams("apellido") != null ? request.queryParams("apellido") : "";
                String matricula = request.queryParams("matricula") != null ? request.queryParams("matricula") : "";
                String telefono = request.queryParams("telefono") != null ? request.queryParams("telefono") : "";
                est.get(idx).asignarApellido(apellido);
                est.get(idx).asignarMatricula(Integer.parseInt(matricula));
                est.get(idx).asignarNombre(nombre);
                est.get(idx).asignarTelefono(telefono);

                Template editarTemplatepost = configuration.getTemplate("templates/form.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("matricula", matricula);
                map.put("nombre", nombre);
                map.put("apellido", apellido);
                map.put("telefono",telefono);

                map.put("lista",est);

                editarTemplatepost.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }

           return writer;
        });

        Spark.get("/eliminar", (request, response) -> {

            StringWriter writer = new StringWriter();
            try {
                String indice = request.queryParams("indice");
                int idx = Integer.parseInt(indice);
                Estudiante student = est.get(idx);
                Template eliminarTemplate = configuration.getTemplate("templates/eliminar.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("matricula", student.obtenerMatricula());
                map.put("nombre", student.obtenerNombre());
                map.put("apellido", student.obtenerApellido());
                map.put("telefono",student.obtenerTelefono());
                map.put("indice",indice);


                map.put("lista",est);

                eliminarTemplate.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }

            return writer;
        });

        Spark.post("/eliminar", (request, response) -> {

            StringWriter writer = new StringWriter();
            try {
                String indice = request.queryParams("indice");
                int idx = Integer.parseInt(indice);
               est.remove(idx);

                Template eliminarTemplatepost = configuration.getTemplate("templates/form.ftl");
                Map<String, Object> map = new HashMap<>();

                map.put("lista",est);

                eliminarTemplatepost.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }

            return writer;
        });
        Spark.get("/consultar", (request, response) -> {

            StringWriter writer = new StringWriter();
            try {
                String indice = request.queryParams("indice");
                int idx = Integer.parseInt(indice);
                Estudiante student = est.get(idx);

                Template consultarTemplate = configuration.getTemplate("templates/consultar.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("matricula", student.obtenerMatricula());
                map.put("nombre", student.obtenerNombre());
                map.put("apellido", student.obtenerApellido());
                map.put("telefono",student.obtenerTelefono());
                map.put("indice",indice);

                map.put("lista",est);

                consultarTemplate.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }

            return writer;
        });


    }
}
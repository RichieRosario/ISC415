

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Main {

    public static void main(String[] args) {
       final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");

        Spark.get("/", (request, response) -> {

            StringWriter writer = new StringWriter();
                  try {
                        Template formTemplate = configuration.getTemplate("templates/form.ftl");
                       Map<String, Object> map = new HashMap<>();
                       formTemplate.process(map, writer);
                    } catch (Exception e) {
                        Spark.halt(500);
                    }






            return writer;
        });

    }
}
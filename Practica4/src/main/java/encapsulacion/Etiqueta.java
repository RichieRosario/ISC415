package encapsulacion;

import java.io.Serializable;

public class Etiqueta implements Serializable{

    private Long id;
    private String etiqueta;

    public Etiqueta(){

        super();
    }
    public Etiqueta(Long id, String etiqueta) {
        this.id = id;
        this.etiqueta = etiqueta;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }



}
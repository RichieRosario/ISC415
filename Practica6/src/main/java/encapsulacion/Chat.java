package encapsulacion;

public class Chat {
    private String usuario_origen;
    private String mensaje;
    private String usuario_destino;
    private boolean inicializando;
    private boolean finalizando=false;

    public boolean isFinalizando() {
        return finalizando;
    }

    public void setFinalizando(boolean finalizando) {
        this.finalizando = finalizando;
    }

    private String nombre_origen="";

    public String getNombre_origen() {
        return nombre_origen;
    }

    public void setNombre_origen(String nombre_origen) {
        this.nombre_origen = nombre_origen;
    }

    public Chat(String usuario_origen, String mensaje, String usuario_destino, boolean inicializando, String nombre_origen) {
        this.usuario_origen = usuario_origen;
        this.mensaje = mensaje;
        this.usuario_destino = usuario_destino;
        this.inicializando = inicializando;
        this.nombre_origen = nombre_origen;
    }

    public String getUsuario_origen() {
        return usuario_origen;
    }

    public void setUsuario_origen(String usuario_origen) {
        this.usuario_origen = usuario_origen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario_destino() {
        return usuario_destino;
    }

    public void setUsuario_destino(String usuario_destino) {
        this.usuario_destino = usuario_destino;
    }

    public boolean isInicializando() {
        return inicializando;
    }

    public void setInicializando(boolean inicializando) {
        this.inicializando = inicializando;
    }
}

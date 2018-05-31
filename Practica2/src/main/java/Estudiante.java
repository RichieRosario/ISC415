public class Estudiante {
    private int matricula;
    private  String nombre;
    private  String apellido;
    private  String telefono;


    public Estudiante(int matricula, String nombre, String apellido, String telefono) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public void asignarNombre(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerApellido() {
        return apellido;
    }

    public void asignarApellido(String apellido) {
        this.apellido = apellido;
    }

    public int obtenerMatricula() {
        return matricula;

    }

    public void asignarMatricula(int matricula) {
        this.matricula = matricula;
    }


    public String obtenerTelefono() {
        return telefono;
    }

    public void asignarTelefono(String telefono) {
        this.telefono = telefono;
    }
}

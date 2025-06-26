public class Proveedor {
    private int id;
    private String nombre;
    private String contacto;

    public Proveedor(int id, String nombre, String contacto) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public Proveedor(String nombre, String contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getContacto() { return contacto; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setContacto(String contacto) { this.contacto = contacto; }
}
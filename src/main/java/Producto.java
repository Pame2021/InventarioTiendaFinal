// Producto.java (Corregido)
public class Producto {
    private int id;
    private String nombre;
    private int precio; // <-- CAMBIO: de double a int
    private int stock;
    private int idProveedor;

    // Constructor con ID
    public Producto(int id, String nombre, int precio, int stock, int idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    // Constructor sin ID
    public Producto(String nombre, int precio, int stock, int idProveedor) { // <-- CAMBIO
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; } // <-- CAMBIO
    public int getStock() { return stock; }
    public int getIdProveedor() { return idProveedor; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(int precio) { this.precio = precio; } // <-- CAMBIO
    public void setStock(int stock) { this.stock = stock; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
}
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instanciamos todos los DAO al inicio
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        MovimientoDAO movimientoDAO = new MovimientoDAO();

        Scanner sc = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("\n=== MENÚ DE GESTIÓN DE INVENTARIO ===");
            System.out.println("--- Proveedores ---");
            System.out.println("1. Agregar proveedor");
            System.out.println("2. Listar proveedores");
            System.out.println("3. Editar proveedor");
            System.out.println("4. Eliminar proveedor");
            System.out.println("--- Productos ---");
            System.out.println("5. Agregar producto");
            System.out.println("6. Listar productos");
            System.out.println("7. Editar producto");
            System.out.println("8. Eliminar producto");
            System.out.println("--- Stock ---");
            System.out.println("9. Registrar movimiento de stock");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt(); sc.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Contacto: ");
                    String contacto = sc.nextLine();
                    proveedorDAO.agregarProveedor(new Proveedor(nombre, contacto));
                }
                case 2 -> {
                    List<Proveedor> lista = proveedorDAO.listarProveedores();
                    System.out.println("\n--- Lista de Proveedores ---");
                    for (Proveedor p : lista) {
                        System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Contacto: " + p.getContacto());
                    }
                }
                case 3 -> {
                    System.out.print("ID del proveedor a editar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Nuevo contacto: ");
                    String contacto = sc.nextLine();
                    proveedorDAO.actualizarProveedor(new Proveedor(id, nombre, contacto));
                }
                case 4 -> {
                    System.out.print("ID del proveedor a eliminar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    proveedorDAO.eliminarProveedor(id);
                }
                case 5 -> { // Agregar producto
                    System.out.print("Nombre del producto: ");
                    String nombre = sc.nextLine();
                    System.out.print("Precio unitario: ");
                    int precio = sc.nextInt(); // <-- CAMBIO: de double a int y de nextDouble a nextInt
                    System.out.print("Stock inicial: ");
                    int stock = sc.nextInt();
                    System.out.print("ID del proveedor: ");
                    int idProv = sc.nextInt(); sc.nextLine();
                    productoDAO.agregarProducto(new Producto(nombre, precio, stock, idProv));
                }
                case 6 -> { // Listar productos
                    List<Producto> productos = productoDAO.listarProductos();
                    System.out.println("\n--- Lista de Productos ---");
                    for (Producto p : productos) {
                        System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() +
                                " | Precio: $" + p.getPrecio() + // Esto funcionará correctamente con un int
                                " | Stock: " + p.getStock() +
                                " | ID Proveedor: " + p.getIdProveedor());
                    }
                }
                case 7 -> { // Editar producto
                    System.out.print("ID del producto a editar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Nuevo precio: ");
                    int precio = sc.nextInt(); // <-- CAMBIO: de double a int y de nextDouble a nextInt
                    System.out.print("Nuevo stock: ");
                    int stock = sc.nextInt();
                    System.out.print("Nuevo ID proveedor: ");
                    int idProv = sc.nextInt(); sc.nextLine();
                    productoDAO.actualizarProducto(new Producto(id, nombre, precio, stock, idProv));
                }
                case 8 -> {
                    System.out.print("ID del producto a eliminar: ");
                    int id = sc.nextInt(); sc.nextLine();
                    productoDAO.eliminarProducto(id);
                }
                case 9 -> {
                    System.out.print("ID del producto: ");
                    int idProducto = sc.nextInt(); sc.nextLine();

                    System.out.print("Tipo de movimiento (ENTRADA o SALIDA): ");
                    String tipo = sc.nextLine().toUpperCase();

                    if (!tipo.equals("ENTRADA") && !tipo.equals("SALIDA")) {
                        System.out.println("Error: Tipo de movimiento inválido.");
                        break;
                    }

                    System.out.print("Cantidad: ");
                    int cantidad = sc.nextInt(); sc.nextLine();

                    if (cantidad <= 0) {
                        System.out.println("Error: La cantidad debe ser un número positivo.");
                        break;
                    }

                    movimientoDAO.registrarMovimiento(tipo, cantidad, idProducto);
                }
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }

        } while (opcion != 0);

        sc.close();
    }
}
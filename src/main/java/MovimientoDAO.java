import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovimientoDAO {

    public void registrarMovimiento(String tipo, int cantidad, int idProducto) {
        // Dos operaciones: registrar el movimiento y actualizar el stock. Deben ser atómicas.
        String sqlInsertMovimiento = "INSERT INTO Movimiento (tipo, cantidad, id_producto) VALUES (?, ?, ?)";
        String sqlUpdateStock;

        // Se usa una consulta condicional para la salida para evitar race conditions
        if (tipo.equals("SALIDA")) {
            sqlUpdateStock = "UPDATE Producto SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";
        } else { // ENTRADA
            sqlUpdateStock = "UPDATE Producto SET stock = stock + ? WHERE id_producto = ?";
        }

        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false); // INICIO DE LA TRANSACCIÓN

            // 1. Actualizar el stock del producto
            int filasAfectadas;
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateStock)) {
                psUpdate.setInt(1, cantidad);
                psUpdate.setInt(2, idProducto);
                if (tipo.equals("SALIDA")) {
                    psUpdate.setInt(3, cantidad); // Parámetro extra para la condición "stock >= ?"
                }
                filasAfectadas = psUpdate.executeUpdate();
            }

            // Si no se actualizó ninguna fila, el producto no existe o no hay stock
            if (filasAfectadas == 0) {
                if (tipo.equals("SALIDA")) {
                    System.out.println("❌ Error: Stock insuficiente o producto no encontrado.");
                } else {
                    System.out.println("❌ Error: Producto con ID " + idProducto + " no fue encontrado.");
                }
                conn.rollback(); // Revertir (aunque no se haya hecho nada, es buena práctica)
                return;
            }

            // 2. Si el stock se actualizó, registrar el movimiento
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsertMovimiento)) {
                psInsert.setString(1, tipo);
                psInsert.setInt(2, cantidad);
                psInsert.setInt(3, idProducto);
                psInsert.executeUpdate();
            }

            // 3. Si ambas operaciones tienen éxito, confirmar la transacción
            conn.commit();
            System.out.println("✅ Movimiento registrado y stock actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("❌ Error crítico durante la transacción. Revirtiendo todos los cambios.");
            if (conn != null) {
                try {
                    conn.rollback(); // REVERTIR TRANSACCIÓN EN CASO DE CUALQUIER ERROR
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // Siempre cerrar la conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
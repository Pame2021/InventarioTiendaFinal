// ProductoDAO.java (Corregido)
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void agregarProducto(Producto p) {
        String sql = "INSERT INTO Producto (nombre, precio_unitario, stock, id_proveedor) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, p.getNombre());
                stmt.setInt(2, p.getPrecio()); // <-- CAMBIO: de setDouble a setInt
                stmt.setInt(3, p.getStock());
                stmt.setInt(4, p.getIdProveedor());
                stmt.executeUpdate();
            }
            conn.commit();
            System.out.println("✅ Producto agregado correctamente.");
        } catch (SQLException e) {
            // ... (código de rollback y cierre sin cambios)
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto ORDER BY id_producto";
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getInt("precio_unitario"), // <-- CAMBIO: de getDouble a getInt
                        rs.getInt("stock"),
                        rs.getInt("id_proveedor")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarProducto(Producto p) {
        String sql = "UPDATE Producto SET nombre = ?, precio_unitario = ?, stock = ?, id_proveedor = ? WHERE id_producto = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, p.getNombre());
                stmt.setInt(2, p.getPrecio()); // <-- CAMBIO: de setDouble a setInt
                stmt.setInt(3, p.getStock());
                stmt.setInt(4, p.getIdProveedor());
                stmt.setInt(5, p.getId());
                stmt.executeUpdate();
            }
            conn.commit();
            System.out.println("✅ Producto actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar producto. Revirtiendo cambios.");
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void eliminarProducto(int id) {
        String sql = "DELETE FROM Producto WHERE id_producto = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            conn.commit();
            System.out.println("✅ Producto eliminado.");

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar producto. Revirtiendo cambios.");
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
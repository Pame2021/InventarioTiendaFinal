import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public void agregarProveedor(Proveedor p) {
        String sql = "INSERT INTO Proveedor (nombre, contacto) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false); // 1. Iniciar transacción

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, p.getNombre());
                stmt.setString(2, p.getContacto());
                stmt.executeUpdate();
            }

            conn.commit(); // 2. Confirmar transacción si todo va bien
            System.out.println("✅ Proveedor agregado exitosamente.");

        } catch (SQLException e) {
            System.out.println("❌ Error al agregar proveedor. Revirtiendo cambios.");
            if (conn != null) {
                try {
                    conn.rollback(); // 3. Revertir transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 4. Cerrar conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";
        // La lectura no necesita transacción explícita
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Proveedor p = new Proveedor(
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre"),
                        rs.getString("contacto")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarProveedor(Proveedor p) {
        String sql = "UPDATE Proveedor SET nombre = ?, contacto = ? WHERE id_proveedor = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, p.getNombre());
                stmt.setString(2, p.getContacto());
                stmt.setInt(3, p.getId());
                stmt.executeUpdate();
            }

            conn.commit();
            System.out.println("✅ Proveedor actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar proveedor. Revirtiendo cambios.");
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

    public void eliminarProveedor(int id) {
        String sql = "DELETE FROM Proveedor WHERE id_proveedor = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            conn.commit();
            System.out.println("✅ Proveedor eliminado.");

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar proveedor. Revirtiendo cambios.");
            // Podría ser por una restricción de clave foránea (proveedor con productos)
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
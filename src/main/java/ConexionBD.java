import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/TiendaInventario";
    private static final String USUARIO = "postgres";
    private static final String CLAVE = "12345678";

    private static HikariDataSource ds;

    // Bloque estático para configurar el pool una sola vez cuando la clase se carga
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USUARIO);
        config.setPassword(CLAVE);

        // Configuraciones opcionales pero recomendadas del pool
        config.setMaximumPoolSize(10); // Máximo de conexiones en el pool
        config.setMinimumIdle(5);      // Mínimo de conexiones inactivas esperando
        config.setConnectionTimeout(30000); // Tiempo de espera para obtener una conexión (30s)
        config.setIdleTimeout(600000); // Tiempo que una conexión puede estar inactiva (10min)
        config.setMaxLifetime(1800000); // Tiempo máximo de vida de una conexión (30min)

        ds = new HikariDataSource(config);
    }

    public static Connection conectar() throws SQLException {
        return ds.getConnection();
    }

    public static void cerrarPool() {
        if (ds != null) {
            ds.close();
        }
    }
}


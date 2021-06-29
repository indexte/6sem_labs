package dataConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class ConnectionPool {
    private final Logger log = LogManager.getLogger(ConnectionPool.class);

    private final String url = "jdbc:postgresql://localhost:5432/faculty";
    private final String user = "postgres";
    private final String password = "admin";
    private final int ConnCnt = 15;

  private final List<Connection> availableConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();

    public ConnectionPool() {
        for (int count = 0; count < ConnCnt; count++) {

            availableConnections.add(this.createConnection());
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            log.info("Connection can`t be created");
        }
        return connection;
    }


    public Connection getConnection() {
        if (availableConnections.size() == 0) {
            log.info("All connections are used");
            return null;
        } else {
            Connection connection =availableConnections.remove(availableConnections.size() - 1);
            usedConnections.add(connection);
            log.info("Created connection, free connections:{}",getAvailableConnsCnt());
            return connection;
        }

    }

    public int getAvailableConnsCnt() {
        return availableConnections.size();
    }
}

import dataConnection.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {


    @Test
    public void shouldGetConnectionAndReturnBackToPool() throws SQLException {
        ConnectionPool connectionPool = new ConnectionPool();
        Connection connection = connectionPool.getConnection();
        Assert.assertEquals(29, connectionPool.getAvailableConnsCnt());
        connection.close();
        Assert.assertEquals(30, connectionPool.getAvailableConnsCnt());
    }
}

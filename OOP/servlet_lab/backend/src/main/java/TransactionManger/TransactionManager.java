package TransactionManger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionManager {
    private static final Logger logger = LogManager.getLogger(TransactionManager.class);

    public static void beginTransaction(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("BEGIN TRANSACTION");
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Transaction could not be started:{}", e.getMessage());
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("COMMIT");
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Transaction could not be commited:{}", e.getMessage());
        }
    }

    public static void rollbackTransaction(Connection connection){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("ROLLBACK");
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Transaction could not be rollback:{}", e.getMessage());
        }
    }
}

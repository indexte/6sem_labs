package dao;

import entity.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDao implements AutoCloseable{
    private final Logger logger = LogManager.getLogger(UserDao.class);
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> findByEmail(String email){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.user WHERE name= ?");
            preparedStatement.setString(1, email);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next())
                return Optional.of(new User()
                        .setId(set.getLong("id"))
                        .setUsername(set.getString("name"))
                        .setEmail(set.getString("email"))
                        .setPassword(set.getString("password"))
                        .setRole(set.getString("role")));

        } catch (SQLException ex) {
            logger.warn("Could not find user with email address {}: {}", email, ex.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> create(User entity) throws Exception {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO user (email,name,password, role) VALUES(?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getUsername());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setString(4, "ROLE_USER");
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Error User can`t be created");
            } else {
                System.out.println("User successfully created");
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    } else {
                        System.out.println("No id obtained. Cannot create");
                    }
                }
            }
        } catch (SQLException ex) {
            logger.warn("User already exist : {}", ex.getMessage());
        }

        return Optional.of(entity);
    }

    public User findById(long id) {
        throw new UnsupportedOperationException();
    }

    public List<User> findAll() {
        throw new UnsupportedOperationException();
    }

    public Optional<User> update(User entity) {
        throw new UnsupportedOperationException();
    }

    public void close() throws Exception {
        connection.close();

    }
}

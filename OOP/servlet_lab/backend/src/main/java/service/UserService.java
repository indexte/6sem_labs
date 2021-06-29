package service;

import dao.UserDao;
import dataConnection.ConnectionPool;
import entity.user.User;

public class UserService {

    private final ConnectionPool connectionPool = new ConnectionPool();

    public UserService(){
    }

    public User loginUser(String name, String password) throws Exception {
        User resultOfUser;
        try (UserDao userDao = new UserDao(connectionPool.getConnection())) {
            resultOfUser = userDao.findByEmail(name)
                    .orElseThrow(() -> new RuntimeException("New user"));
        }
        if (resultOfUser.getPassword().equals(password))
            return resultOfUser;
        throw new IllegalArgumentException("Wrong password");
    }

}

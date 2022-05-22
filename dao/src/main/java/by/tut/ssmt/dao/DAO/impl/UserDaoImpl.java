package by.tut.ssmt.dao.DAO.impl;

import by.tut.ssmt.dao.DAO.AbstractDao;
import by.tut.ssmt.dao.DAO.ConnectionPool;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM users";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE user_name = ? AND password = ?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE user_name = ?";
    private static final String SELECT_FROM_TABLE_WHERE = "SELECT * FROM users WHERE user_id = ?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO users (user_name, password) VALUES (?, ?)";
    private static final String UPDATE_TABLE = "UPDATE users SET password = ?, WHERE user_name = ?";
    private static final String DELETE_FROM_TABLE = "DELETE FROM users WHERE user_name = ?";

    public UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());

    public List<User> selectDao() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<User> users = new ArrayList<>();
            connection = getConnection(true);
            preparedStatement = connection.prepareStatement(SELECT_FROM_TABLE);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                User user = new User(userId, userName, password);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    public User find(User user) throws DaoException {
        User userFound = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                userFound = new User(userId, userName, password);
            }
            return userFound;
        } catch (SQLException e) {
            LOGGER.error("Error: ", e);
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    public User selectOneDao(int userId) throws DaoException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(false);
            preparedStatement = connection.prepareStatement(SELECT_FROM_TABLE_WHERE);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                user = new User(id, name, password);
            }
            return user;
        } catch (SQLException e) {
            LOGGER.error("Error: ", e);
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            retrieve(connection);
        }
    }

    public boolean insert(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            connection.setAutoCommit(false);
            preparedStatement1 = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement1.setString(1, user.getUserName());
            resultSet = preparedStatement1.executeQuery();
            User userMatch = new User();
            if (resultSet.next()) {
                userMatch.setUserId(resultSet.getInt(1));
                userMatch.setName(resultSet.getString(2));
                userMatch.setPassword(resultSet.getString(3));
            }
            if (userMatch.getUserName() == null) {
                preparedStatement2 = connection.prepareStatement(INSERT_INTO_TABLE);
                preparedStatement2.setString(1, user.getUserName());
                preparedStatement2.setString(2, user.getPassword());
                result = preparedStatement2.executeUpdate();
            }
            connection.commit();
            return (result != 0);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    public boolean update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = getConnection(false);
            connection.setAutoCommit(false);
            preparedStatement1 = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement1.setString(1, user.getUserName());
            resultSet = preparedStatement1.executeQuery();
            User userMatch = new User();
            if (resultSet.next()) {
                userMatch.setUserId(resultSet.getInt(1));
                userMatch.setName(resultSet.getString(2));
                userMatch.setPassword(resultSet.getString(3));
            }
            if (userMatch.getUserName() == null) {
                preparedStatement2 = connection.prepareStatement(UPDATE_TABLE);
                preparedStatement2.setString(1, user.getPassword());
                preparedStatement2.setString(2, user.getUserName());
                result = preparedStatement2.executeUpdate();
            }
            connection.commit();
            return (result != 0);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(resultSet);
            close(preparedStatement1, preparedStatement2);
            retrieve(connection);
        }
    }

    public void delete(String userName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection(false);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE_FROM_TABLE);
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Error while rolling back", ex);
            }
            throw new DaoException("Error in UserDao", e);
        } finally {
            close(null, preparedStatement);
            retrieve(connection);
        }
    }
}
package by.tut.ssmt.service.impl;

import by.tut.ssmt.dao.DAO.DaoFactory;
import by.tut.ssmt.dao.DAO.UserDao;
import by.tut.ssmt.dao.domain.User;
import by.tut.ssmt.dao.exception.DaoException;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exception.ServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getInstance().getUserDao();

    public UserServiceImpl() {
    }

    @Override
    public List<User> selectAllService() throws ServiceException {
        try {
            return userDao.selectDao();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean loginService(User user) throws ServiceException {
        try {
            user = userDao.find(user);
            return user != null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User selectOneDaoService(int userId) throws ServiceException {
        try {
            return userDao.selectOneDao(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insertService(User user) throws ServiceException {
        try {
            return userDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateService(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteService(String userName) throws ServiceException {
//    public void deleteService(int userId) {
//        userDao.delete(userId);
        try {
            userDao.delete(userName);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}

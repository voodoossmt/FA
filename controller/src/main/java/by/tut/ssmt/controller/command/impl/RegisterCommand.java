package by.tut.ssmt.controller.command.impl;

import by.tut.ssmt.controller.command.Command;
import by.tut.ssmt.controller.exception.ControllerException;
import by.tut.ssmt.controller.services.formDataCollectors.UserFormDataCollector;
import by.tut.ssmt.dao.repository.entities.User;
import by.tut.ssmt.service.ServiceFactory;
import by.tut.ssmt.service.UserService;
import by.tut.ssmt.service.exceptions.NullOrEmptyException;
import by.tut.ssmt.service.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {

    boolean userAdded;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    final UserFormDataCollector dataCollector = new UserFormDataCollector();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        try {
            User user = dataCollector.collectFormData(request);
            userAdded = userService.insertService(user);
            postToMainPage(request, response);
        } catch (NullOrEmptyException e) {
            request.setAttribute("message", "Please fill out the form");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void postToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userAdded) {
            response.sendRedirect("/main");
        } else {
            request.setAttribute("message", "User name or/and password are already in use, try one more time");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }
}


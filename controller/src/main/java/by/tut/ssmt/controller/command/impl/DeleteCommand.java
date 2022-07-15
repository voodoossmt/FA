package by.tut.ssmt.controller.command.impl;import by.tut.ssmt.controller.command.Command;import by.tut.ssmt.controller.exception.ControllerException;import by.tut.ssmt.service.ProductService;import by.tut.ssmt.service.ServiceFactory;import by.tut.ssmt.service.exception.ServiceException;import javax.servlet.ServletException;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;import java.io.IOException;import java.util.logging.Logger;public class DeleteCommand implements Command {    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();    private final ProductService productService = serviceFactory.getProductService();    private static final Logger LOGGER = Logger.getLogger(DeleteCommand.class.getName());    @Override    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {        try {            final String product = request.getParameter("productName");            productService.deleteService(product);            LOGGER.info("RequestURL: " + request.getRequestURL());            response.sendRedirect("/main");        } catch (ServiceException e) {            throw new ControllerException(e);        }    }}
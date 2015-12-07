package by.itechart.javalab.servlet;

import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.controller.ControllerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FrontController extends HttpServlet {
    private static Logger log = LogManager.getLogger(FrontController.class.getName());

    public void init(ServletConfig servletConfig) throws ServletException{

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Controller controller = ControllerFactory.getController(request.getPathInfo());
        controller.doGet(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Controller controller = ControllerFactory.getController(request.getPathInfo());
        controller.doPost(request, response);
    }
}

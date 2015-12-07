package by.itechart.javalab.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class RequestFilter implements Filter {
    private static Logger log = LogManager.getLogger(RequestFilter.class.getName());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getContextPath();
        if (path.startsWith("/resources")) {
            servletRequest.getRequestDispatcher(path).forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

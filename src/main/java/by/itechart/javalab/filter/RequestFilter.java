package by.itechart.javalab.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class RequestFilter implements Filter {
    private static Logger log = LogManager.getLogger(RequestFilter.class.getName());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        if (path.contains("/resources")) {
            if (path.contains("/resources/files")) {
                String fileName = path.split("/")[3];
                String realFileName = fileName.substring(fileName.indexOf("_") + 1);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment;filename=\"" + realFileName + "\"");

                ServletContext ctx = request.getServletContext();
                InputStream is = ctx.getResourceAsStream(path);

                int read = 0;
                byte[] bytes = new byte[1024];
                OutputStream os = response.getOutputStream();

                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                os.flush();
                os.close();
            } else {
                servletRequest.getRequestDispatcher(path).forward(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

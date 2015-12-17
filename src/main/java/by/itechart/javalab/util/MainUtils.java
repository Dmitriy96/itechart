package by.itechart.javalab.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;


public class MainUtils {
    private static Logger log = LogManager.getLogger(MainUtils.class.getName());
    private static final String email;

    static {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            log.error(e);
        }
        email = properties.getProperty("email");
    }

    public static String getEmail() {
        return email;
    }
}

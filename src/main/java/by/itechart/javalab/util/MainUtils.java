package by.itechart.javalab.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class MainUtils {
    private static Logger log = LogManager.getLogger(MainUtils.class.getName());
    private static final String email;
    private static final List<String> templates;

    static {
        Properties configProperties = new Properties();
        try {
            configProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            log.error(e);
        }
        email = configProperties.getProperty("email");
        templates = Arrays.asList(configProperties.getProperty("templates").split(","));
    }

    public static String getEmail() {
        return email;
    }

    public static List<String> getTemplates() {
        return templates;
    }
}

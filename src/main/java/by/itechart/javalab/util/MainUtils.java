package by.itechart.javalab.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class MainUtils {
    private static Map<String, String> countries = new HashMap<>();
    private static Map<String, String> genders = new HashMap<>();
    private static Map<String, String> maritalStatuses = new HashMap<>();
    private static Map<String, String> phoneTypes = new HashMap<>();
    private static Logger log = LogManager.getLogger(MainUtils.class.getName());

    /*static {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("controller.properties"));
        } catch (IOException e) {
            log.error(e);
        }
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            try {
                controllerMap.put((String)entry.getKey(), (Controller)Class.forName((String)entry.getValue()).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                log.error(e);
            }
        }
    }*/

    public static Map<String, String> getCountries() {
        return countries;
    }

    public static Map<String, String> getGender() {
        return genders;
    }

    public static Map<String, String> getMaritalStatuses() {
        return maritalStatuses;
    }

    public static Map<String, String> getPhoneTypes() {
        return phoneTypes;
    }
}

package by.itechart.javalab.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ControllerFactory {
    private static ConcurrentMap<String, String> controllerMap = new ConcurrentHashMap<>();
    private static Logger log = LogManager.getLogger(ControllerFactory.class.getName());

    static {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("controller.properties"));
        } catch (IOException e) {
            log.error(e);
        }
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            controllerMap.put((String)entry.getKey(), (String)entry.getValue());
        }
    }

    public static Controller getController(String path) {
        String action = path.split("/")[1];    // URI format: /{action}/*
        Controller controller = null;
        try {
            controller = (Controller) Class.forName(controllerMap.get(action)).newInstance();
            if (controller == null) {
                controller = (Controller) Class.forName(controllerMap.get("error")).newInstance();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            log.error(ex);
        }
        return controller;
    }
}

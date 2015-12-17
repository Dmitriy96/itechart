package by.itechart.javalab.servlet;

import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.controller.ControllerFactory;
import by.itechart.javalab.controller.impl.BirthdayNotificationController;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.Calendar;


public class FrontController extends HttpServlet {
    private static Logger log = LogManager.getLogger(FrontController.class.getName());

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        JobDetail job = JobBuilder.newJob(BirthdayNotificationController.class)
                .withIdentity("birthdayNotification", "group1").build();
        Date todaysMidnight = new Date();
        todaysMidnight = DateUtils.truncate(todaysMidnight, Calendar.DAY_OF_MONTH);
        todaysMidnight = DateUtils.addDays(todaysMidnight, 1);
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("birthdayNotification", "group1")
                .startAt(todaysMidnight)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInHours(24)
                                .repeatForever())
                .build();
        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            log.error(e);
        }
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

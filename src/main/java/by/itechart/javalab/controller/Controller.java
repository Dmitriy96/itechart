package by.itechart.javalab.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Controller {
	void doGet(HttpServletRequest request, HttpServletResponse response);
	void doPost(HttpServletRequest request, HttpServletResponse response);
}

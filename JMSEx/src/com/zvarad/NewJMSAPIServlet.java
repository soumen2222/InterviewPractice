package com.zvarad;

import java.io.IOException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewJMSAPIServlet
 */
@ApplicationScoped
@WebServlet("/NewJMSAPIServlet")

public class NewJMSAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	JMSContext jmsContext;
	
	@Resource(lookup="java:/jms/demoQueue")
	Queue demoQueue;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewJMSAPIServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		JMSProducer producer = jmsContext.createProducer();
		producer.send(demoQueue,request.getParameter("msg"));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

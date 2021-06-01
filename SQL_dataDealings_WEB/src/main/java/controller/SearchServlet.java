package controller;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data_base.DataHandle;
import data_base.DataPrepare;
import data_base.ResultBean;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DataHandle.connectSQL();
		
		ResultBean bearBean = new ResultBean();
		ResultBean masterBean = new ResultBean();
		ResultBean ikeaBean = new ResultBean();
		
		DataPrepare dataToReturn = new DataPrepare();
		

		
		String emptyString = "";
		String searchString = request.getParameter("searchString");
		DataHandle dataHandle = new DataHandle(searchString, bearBean, masterBean, ikeaBean);
	
		
		ArrayList<ArrayList<String>> mostRelevant = dataToReturn.collectMostRelevantResults(bearBean, masterBean, ikeaBean);
	
		
		ArrayList<ArrayList<String>> relevant = dataToReturn.collectRelevantResults(bearBean, masterBean, ikeaBean);
		
		ArrayList<ArrayList<String>> leastRelevant = dataToReturn.collectLeastRelevantResults(bearBean, masterBean, ikeaBean);
		
		
		// check if connection is open?
		if (DataHandle.connectSQL()) {
			// send the string

			request.setAttribute("mostRelevant", mostRelevant);
			request.setAttribute("relevant", relevant);
			request.setAttribute("leastRelevant", leastRelevant);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		} else {

			request.setAttribute("", emptyString);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.include(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}

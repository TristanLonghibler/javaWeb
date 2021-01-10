package edu.umsl.product.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.product.beans.Product;

@WebServlet("/prepare")
public class PrepareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement allprods;
	String cities = "";
	
	public void init(ServletConfig config) throws ServletException {
		ServletContext ctx = config.getServletContext();
		
		cities = ctx.getInitParameter("cities");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productdb", "root", "");

			allprods = connection.prepareStatement("SELECT prod_id, prod_name FROM product ORDER BY prod_id");
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher dispatcher = null;
		String browserInfo = request.getHeader("User-Agent");

		if (browserInfo.contains("Win")) {
			dispatcher = request.getRequestDispatcher("inputwin.jsp");
		} else {
			dispatcher = request.getRequestDispatcher("inputmac.jsp");
		}
		
		String[] cityArr = cities.split(",");
		
		request.setAttribute("cities", cityArr);
		
		List<Product> prodlist = new ArrayList<Product>();

		try {
			ResultSet resultsRS = allprods.executeQuery();

			while (resultsRS.next()) {
				Product crtprod = new Product();

				crtprod.setId(resultsRS.getInt(1));
				crtprod.setName(resultsRS.getString(2));

				prodlist.add(crtprod);
			}
			resultsRS.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		request.setAttribute("prodlist", prodlist);

		

		dispatcher.forward(request, response);
	}

}

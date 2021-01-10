package edu.umsl.product.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.product.beans.OrderBean;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement getprice, addpurchase;
	String rates = "";

	public void init(ServletConfig config) throws ServletException {
		ServletContext ctx = config.getServletContext();
		
		rates = ctx.getInitParameter("tax rates");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productdb", "root", "");

			getprice = connection.prepareStatement("SELECT price FROM product WHERE prod_id = ?");
			
			addpurchase = connection.prepareStatement("INSERT INTO purchase (prod_id, amount, tax) VALUES (?,?,?)");
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cityId = request.getParameter("cityid");
		String prodId = request.getParameter("prodid");
		String odr = request.getParameter("ordernum");
		int cityIdnum = 0;
		int prodIdnum = 0;
		int odrnum = 0;

		try {
			cityIdnum = Integer.parseInt(cityId);
			prodIdnum = Integer.parseInt(prodId);
			odrnum = Integer.parseInt(odr);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		String[] rateArr = rates.split(",");
		
		String crtrate = rateArr[cityIdnum];
		
		double crtprice = 0.0;
		double crtratenum = 0.0;
		DecimalFormat twoDigits = new DecimalFormat("0.00");
		
		try {
			getprice.setInt(1, prodIdnum);
			ResultSet priceRS = getprice.executeQuery();
			priceRS.next();
			
			crtprice = priceRS.getFloat(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		try {
			crtratenum = Double.parseDouble(crtrate);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		double amount = crtprice * odrnum;
		String amountstr = twoDigits.format(amount);
		String crttax = twoDigits.format(amount * crtratenum / 100);
		
		float famt = 0.0f;
		float ftax = 0.0f;
		
		try {
			famt = Float.parseFloat(amountstr);
			ftax = Float.parseFloat(crttax);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		try {
			addpurchase.setInt(1,  prodIdnum);
			addpurchase.setFloat(2, famt);
			addpurchase.setFloat(3, ftax);
			
			addpurchase.executeUpdate();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		OrderBean odrbean = new OrderBean();
		
		odrbean.setAmount(amountstr);
		odrbean.setTax(crttax);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
		
		request.setAttribute("odrbean", odrbean);

		dispatcher.forward(request, response);
	}

}

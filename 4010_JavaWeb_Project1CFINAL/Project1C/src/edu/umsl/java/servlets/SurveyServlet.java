package edu.umsl.java.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.java.beans.BrowserNameBean;
import edu.umsl.java.beans.BrowserSurveyBean;

@WebServlet("/survey")
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement allbrowsers, updateVisits1, updateVisits2, allstats, totalVisits;
	
	public void init(ServletConfig config) throws ServletException {
		// attempt database connection and create PreparedStatements
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/browserdb", "root", "");

			connection.prepareStatement("UPDATE `surveyresults` SET `hit_count`=0").executeUpdate();
			
			allbrowsers = connection
					.prepareStatement("SELECT id, browser_name "
							+ "FROM surveyresults ORDER BY id");
			
			
			allstats = connection.prepareStatement("SELECT id, browser_name, hit_count " + "FROM surveyresults ORDER BY id");
			
			totalVisits = connection.prepareStatement("SELECT sum( hit_count ) FROM surveyresults");
			updateVisits1 = connection.prepareStatement("UPDATE surveyresults SET hit_count = hit_count + 1 " + "WHERE id = ?");
			updateVisits2 = connection.prepareStatement("UPDATE surveyresults Set hit_count = hit_count + 2 " + "WHERE id = ?");
			
			
		} 
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	} // end of init method

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("browser.jsp");
		List<BrowserNameBean> brwsnmlist = new ArrayList<BrowserNameBean>();
		
		try {
			ResultSet resultsRS = allbrowsers.executeQuery();
			//zerohit.executeUpdate();
			
			while (resultsRS.next()) {
				
				BrowserNameBean crtbrwsbean = new BrowserNameBean();
				
				crtbrwsbean.setId(resultsRS.getInt(1));
				crtbrwsbean.setBrowserName(resultsRS.getString(2));
				
				brwsnmlist.add(crtbrwsbean);
				
			}
			resultsRS.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		// Get survey data from the database
		
		
		request.setAttribute("browsernamelist", brwsnmlist);
		
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
		int brwsIdnum = 0; 
		DecimalFormat twoDigits = new DecimalFormat("0.00");
		List<BrowserSurveyBean> brwssrvylist = new ArrayList<BrowserSurveyBean>();
		
		String brwsId = request.getParameter("browser");
		
		try {
			brwsIdnum = Integer.parseInt(brwsId); // Use this number to update the database.
			}
		 catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		try {
			if(brwsIdnum > 0) {
				updateVisits1.setInt(1,  brwsIdnum);
				updateVisits1.executeUpdate();
			}
		}catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		
		String browserInfo = request.getHeader("User-Agent");
		
		int mybrwsId = 0;
		
		if (browserInfo.contains("Edg")) {
			mybrwsId = 3;
		} else if (browserInfo.contains("OPR")) {
			mybrwsId = 6;
		} else if (browserInfo.contains("Firefox")) {
			mybrwsId = 2;
		} else if (browserInfo.contains("Chrome") && browserInfo.contains("Safari")) {
			mybrwsId = 1;
		} else {
			mybrwsId = 7;
		}
		
		try {
			if (mybrwsId > 0) {
				updateVisits2.setInt(1,  mybrwsId);
				updateVisits2.executeUpdate();
			}
			ResultSet totalRS = totalVisits.executeQuery();
			totalRS.next();
			int total = totalRS.getInt(1);
			
			ResultSet resultsRS = allstats.executeQuery();
			
			int visits;
			
			while (resultsRS.next()) {
				BrowserSurveyBean brwssrvybean = new BrowserSurveyBean();
				brwssrvybean.setBrowserName(resultsRS.getString(2));
				
				visits = resultsRS.getInt(3);
				
				brwssrvybean.setPercentage(twoDigits.format((double) visits / total * 100));
				
				brwssrvylist.add(brwssrvybean);
			}
			resultsRS.close();
			
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		request.setAttribute("browsersurveylist", brwssrvylist);
		
		dispatcher.forward(request, response);
		
	}

}

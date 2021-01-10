<!DOCTYPE html>
<%@ page import = "java.util.*" %>
<%@ page import = "edu.umsl.java.beans.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Survey Result</title>
</head>
<body>
<h2>Display Survey Result</h2>
<br /><br />
   <%
   		List<BrowserSurveyBean> brwssrvylist = (List<BrowserSurveyBean>) request.getAttribute("browsersurveylist");
   %>
   <ul>
      <%
     for (BrowserSurveyBean crtbean : brwssrvylist) {
    	 %>
    	 <li><%= crtbean.getBrowserName() %>: &nbsp; <%= crtbean.getPercentage() %>%</li>
    	 <%
     }
   %>
   </ul>
   <br /><br />
   <a href="index.jsp">Start New Survey</a>
</body>
</html>
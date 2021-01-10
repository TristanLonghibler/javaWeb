<!DOCTYPE html>
<%@ page import = "java.util.*" %>
<%@ page import = "edu.umsl.java.beans.*" %>
<html>
<head>
   <title>Survey</title>
</head>
<body>
<form method="post" action="survey">

   <p>What is your favorite browser?</p>
   
   <%
   		List<BrowserNameBean> brwsnmlist = (List<BrowserNameBean>) request.getAttribute("browsernamelist");
   %>

   <p>
   <%
     for (BrowserNameBean crtbean : brwsnmlist) {
    	 
    	 %>
    	 <input type = "radio" name = "browser" value = "<%= crtbean.getId() %>" /> &nbsp; <%= crtbean.getBrowserName() %><br />
    	 <%
    	 
     }
   %>
      
      <input type = "radio" name = "browser" value = "0" checked = "checked" /> &nbsp; None
   </p>

   <p><input type = "submit" value = "Submit" /></p>

</form>
</body>
</html>
<!DOCTYPE html>
<%@ page import = "edu.umsl.product.beans.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Result</title>
</head>
<body>
<h2>Display Order Result</h2>
<br /><br />
   <ul>
      <li>Amount: &#36;${odrbean.amount}</li>
      <li>Tax: &#36;${odrbean.tax}</li>
   </ul>
   <br /><br />
   <a href="index.jsp">Start New Order</a>
</body>
</html>
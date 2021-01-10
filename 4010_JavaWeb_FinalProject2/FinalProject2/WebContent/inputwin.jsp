<!DOCTYPE html>
<%@ page import="java.util.*"%>
<%@ page import="edu.umsl.product.beans.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Input Page</title>
</head>
<body>
	<b>Select a city and a product (Windows)</b>
	<br />
	<br />

	<%
		String[] cities = (String[]) request.getAttribute("cities");
		List<Product> prodlist = (List<Product>) request.getAttribute("prodlist");
	%>

	<form action="order" method="post">
		<table>
			<tr>
				<td style="width: 10em"><select name="cityid">
						<%
							for (int i = 0; i < cities.length; i++) {
						%>
						<option value="<%=i%>"><%=cities[i]%></option>
						<%
							}
						%>
				</select></td>
				<td><select name="prodid">
						<%
							for (Product crtprod : prodlist) {
						%>
						<option value="<%=crtprod.getId()%>"><%=crtprod.getName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
			<tr>
			   <td>&nbsp;</td>
			   <td>Quantity: <input type="text" name="ordernum" size="2" /></td>
			</tr>
		</table>
		<br />
		<br /> <input type="submit" name="submit" value="Order" />
	</form>

</body>
</html>
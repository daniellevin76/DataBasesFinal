<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>I want some answers</title>
</head>
<body>



	<form action="<%=request.getContextPath()%>/SearchServlet"
		method="post">
		Get from the database:<input type="text" name="searchString"><br>

		<input type="submit" value="Get me some answers">
	</form>

	<%
	// get the results
	ArrayList<ArrayList<String>> mostRelevant = (ArrayList<ArrayList<String>>)request.getAttribute("mostRelevant");
	
	ArrayList<ArrayList<String>> relevant =  (ArrayList<ArrayList<String>>)request.getAttribute("relevant");
	
	ArrayList<ArrayList<String>> leastRelevant = (ArrayList<ArrayList<String>>)request.getAttribute("leastRelevant");

	// Most relevant results
	if (!(mostRelevant == null)) {

		out.print("<p> Most relevant result</p>");

		out.print("<ul>");

		for (int i = 0; i < mostRelevant.size(); i++) {
			out.print("<li>");
			out.print(mostRelevant.get(i));

			out.print("</li>");
		}
		if (mostRelevant.size() == 0) {
			out.print("This didn't yield any result, maybe you got lucky in the other tables");
		}

		out.print("</ul>");

	}
	
	
	
	
	// Still relevant results
		if (!(relevant == null)) {

			out.print("<p> Still relevant result</p>");

			out.print("<ul>");

			for (int i = 0; i < relevant.size(); i++) {
				out.print("<li>");
				out.print(relevant.get(i));

				out.print("</li>");
			}
			if (relevant.size() == 0) {
				out.print("You wont find the answers you're looking for here. Have you tried our neighbours?");
			}
			

			out.print("</ul>");

		}
	
		// Least relevant
				if (!(leastRelevant == null)) {

					out.print("<p> Least relevant result</p>");

					out.print("<ul>");

					for (int i = 0; i < leastRelevant.size(); i++) {
						out.print("<li>");
						out.print(leastRelevant.get(i));

						out.print("</li>");
					}
					
					if (leastRelevant.size() == 0) {
						out.println("Are you sure you are looking in the right place?");
					}	

					out.print("</ul>");
					
					if(mostRelevant.size() == 0 && relevant.size() ==0 && leastRelevant.size()==0){
						out.println("If all fails, are you sure that you even know what you're searching for?");
					}	

					out.print("</ul>");

				}
	
	%>











</body>
</html>
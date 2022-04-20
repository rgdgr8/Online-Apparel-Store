<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Apparel Home</title>
</head>
<body>
	<form action='out'>
		<input type='submit' value='Logout'>
	</form>
	<h3>User id: ${id}</h3>
	<h2>${pref} in your price range</h2>
	<form action='purchase'>
		<c:forEach var="app" items="${apparels}">
			<h3><c:out value="${app}" /></h3><input type='checkbox' name='icodes[]' value='${app.getIcode()}'>
		</c:forEach>
		<br><br><input type='submit' value='Buy'>
	</form>
	<form action='allApparels'>
		<input type='submit' value='Show all'>
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Cab List</title>
</head>
<body>
<table>
	<thead>
	<tr>
		<th>Car make</th>
		<th>Capacity</th>
		<th>Has baby chair</th>
		<th>Licence plate</th>
		<th>Manufacture year</th>
		<th width="100"></th>
		<th width="100"></th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${cabs}" var="cab">
		<tr>
			<td>${cab.carMake}</td>
			<td>${cab.capacity}</td>
			<td>${cab.hasBabyChair}</td>
			<td>${cab.licencePlate}</td>
			<td>${cab.manufactureYear}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</body>
</html>
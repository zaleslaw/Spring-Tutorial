<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Cab</title>
</head>
<body>
<h1>${message}</h1>
<a href="<c:url value='/cabList' />">List of cabs</a>
</body>
</html>
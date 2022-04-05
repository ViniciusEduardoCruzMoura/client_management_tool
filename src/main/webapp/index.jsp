<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<jsp:include page="import_bootstrap.jspf" />
<jsp:include page="import_jquery.jspf" />
</head>
<body>

<jsp:include page="navbar.jsp" />
<div class="mt-5"></div>

<div class="mt-5">
	<h5 class="d-flex justify-content-center">Vinicius Eduardo Carvalho da Cruz de Moura</h5>
	<h1 class="d-flex justify-content-center display-4">Client Management Tool</h1>
</div>

<div class="mt-4">
	<jsp:include page="sign_in.jsp" />
</div>

</body>
</html>
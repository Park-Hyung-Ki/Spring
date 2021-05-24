<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- board 관련 html 작성하기 -->
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Welcome To Hell!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<select>

<c:forEach items="${boardList}" var="board">	
	<option >
	${board.name}
	 </option>
	</c:forEach>
</select>
</body>
</html>

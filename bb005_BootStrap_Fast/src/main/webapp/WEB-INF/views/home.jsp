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
	Welcome To Hell!~!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<c:forEach items="${boardList}" var="board"> <!-- items는 Controller에서 옵니다. var은 boardList구조에서 나타나는 객체의 이름 -->	
	<a href="/post/list?boardId=${board.id}">${board.name}</a>
	<!-- 그리고 url을 우리가 만드는건데, post가 board 안에 있는거니까 그 클래스 안에있는 객체명을 따라가야한다. -->
	<br>
	</c:forEach>
</body>
</html>

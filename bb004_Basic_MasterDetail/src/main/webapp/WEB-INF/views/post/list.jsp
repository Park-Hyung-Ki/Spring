<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 게시글 목록 보기라 쓰고 여긴 또다른 지옥이라고 읽지.</title>
</head>
<body>
	<table>
		<caption>게시글 목록</caption>
		<tr>
			<th>제목</th>
			<th>내용</th>
			<th>작성자</th>
		</tr>
		<c:forEach items="${listPost}" var="post">
			<tr>
			<td><a href="/post/readPost?postId=${post.id}">${post.title}</a></td>
				<td>${post.content}</td>
				<td>${post.writer.name}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
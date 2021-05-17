<!-- 이걸 만든 이유는 Controller에서 JSP로 Date Value를 보내주는걸 확인하기 위함 -->
<!-- 23. confirmParty.jsp 작성 시작 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>confirmParty</title>
</head>
<body>
	id : ${newbie.id} <!--  24. server로 부터 넘어온 정보 객체의 속성(id)를 GetterMethod를 통해서 확인 -->
	<!-- 이 파일에서는 id라는 값만 예시로 들어서 작성한 것  -->
	<br>
	name : ${newbie.name}
	<!-- 27. name : ${party.name}이렇게 사용한 양식은 26번 주석을 사용한 함수의 형식 때문에 바꿨다.,  -->
	<!-- 34. obj -> newbie 계속 값이 왔다갔다 한다. -->
	<br>
	<!--  내사랑 나이 : ${ageOfMine}-->  
</body>
</html>
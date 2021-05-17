<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Party Class에서 회원가입을 하기위해 만든 부분, Spring에서는 View라는 폴더 밑에 새로운 폴더를 만들어서 jsp파일을 만들어야 한다. -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Party 회원가입 홈페이지</title>
</head>
<body>
	<h3>회원 가입</h3>
	<form action="/party/createPartyRedirect" method="post">
		 계정 이름 :<input type="text" name="name">
		<br>
		<div>
			성별:<br>
			 <input type="radio" name="sex" value="true" checked />
			 <label	for="male">남자</label> <br>
		</div>
		<div>
			<input type="radio" name="sex" value="false" />
			 <label for="female">여자</label>
		</div>
		<br>
		생년월일<input type="date" name="birthDate">
		<br>

		 <input type="hidden" name="listContactPoint[0].ContactPointType" value="addr">
			 주소<input type="text" name="listContactPoint[0].value"><br>
			  <input type="hidden" name="listContactPoint[1].ContactPointType" value="phoneNum">
			 제출<input type="reset" name="listContactPoint[0].value">
		<button type="submit">가입</button>
		<br>
		<!--사랑하는사람의 나이 <input type ="text" name="ageOfMine"> -->
	</form>
</body>
</html>
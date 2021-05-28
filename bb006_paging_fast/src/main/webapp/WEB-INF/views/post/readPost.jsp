<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-body">
			<div class="form-group">
				<%@ include file="./include/postCommon.jsp" %>
				<!-- 공통적 속성인 실 내용들은 postCommon.jsp를 만들어서 보내버렸음 -->
				
			<button data-oper='modify' class="btn btn-primary">수정</button>
			<button data-oper='list' class="btn btn-secondary">목록</button>	
							
			<form id='frmOper' action="/post/modifyPost" method="get">
				<input type="hidden" name="boardId" value="${boardId}">
				<input type="hidden" id="postId" name="postId" value="${post.id}">
				<input type="hidden" name="pageNumber" value="${pagination.pageNumber}">
				<input type="hidden" name="amount" value="${pagination.amount}">
			</form>
			
			<!--  
			<button data-oper='modify' class="btn btn-primary"
			 onclick="location.href='/post/modifyPost?boardId=${boardId}&postId=${post.id}'">수정</button> -->
			<!-- 이 data-oper는 modify라는 변수를 추가해서, html에서 제공하는 element의 변수명의 data를 추가할 수 있는 장치
			<button data-oper='list' class="btn btn-secondary"
			 onclick="location.href='/post/list?boardId=${boardId}'">목록으로</button> -->
			  
		</div>
	</div>
</div>
<!-- /.container-fluid -->

<!-- End of Main Content -->
<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">

$(document).ready(function() {
	
	//postCommon에 있는 함수를 부를 것
	$("button[data-oper='modify']").on("click", function() {
		$("#frmOper").submit();
	});
	
	$("button[data-oper='list']").on("click", function() {
		$("#frmOper").find("#postId").remove();
		$("#frmOper").attr("action", "/post/list").submit();
	});
});
</script>


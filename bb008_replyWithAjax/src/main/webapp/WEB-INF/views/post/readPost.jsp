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
				<input type="hidden" name="searching" value='${pagination.searching}'>
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

<script type="text/javascript" src="\resources\js\post\reply.js"> </script>

<script type="text/javascript">
	$(document).ready(function() {
		
		//postCommon에 있는 함수를 부를 것
		$("button[data-oper='modify']").on("click", function() {
			$("#frmOper").submit();
		});
		
		$("button[data-oper='list']").on("click", function() {
			$("#frmOper").find("#postId").remove();
			$("#frmOper").attr("action", "/post/listBySearch").submit();
		});
		
		
		var originalId = "${post.id}";
		//replyService Module
		replyService.getReplyList(
				{orgId:originalId, page:1},
				function(listReply) {
					for(var i = 0, len = listReply.length || 0; i < len; i++){
						console.log(listReply[i]);
					}
				},
				
				function(errMsg) {
					alert("댓글 등록 오류:" + errMsg);
				} 
			);
		
		replyService.getReply(
				"000030000l",
				function(replyObj) {
					alert("댓글 상세  :" + replyObj);
				},
				function(errMsg) {
					alert("댓글 조회 오류:" + errMsg);
				}
			);
		
		/* reply.js에서 만들어놓은 addReply를 호출하겠다는 의미 */
		replyService.addReply(
			originalId,
			{content:"Test용도로 입력하는 댓글"},
			function(newReplyId) {
				alert("신규 댓글 등록 id:" + newReplyId);
			},
			
			function(errMsg) {
				alert("댓글 등록 오류:" + errMsg);
			// originalId, reply, successCallBack, errorCallBack를 하나씩 채워나갈것
			// 객체 만드는 방법: {속성이름 : 값(내용)}
			//  successCallBack, errorCallBack -> function() {} 함수가 될것, 함수의 기능을 하게 만들것
			} 
		);
		
		replyService.updateReply(
			{
				id:"000030000l",
				content:"폴란드 전의 승리를 확정짓는 골!!"
				
			},
			function(resultMsg) {
				alert("댓글 수정 성공:" + resultMsg);
			},
			
			function(errMsg) {
				alert("댓글 수정 오류:" + errMsg);
			}
		);
		
		replyService.removeReply(
			"0000200013",
			function(delResult) {
				alert("댓글 삭제 성공 :" + delResult);
			},
			function(errMsg) {
				alert("댓글 삭제 오류:" + errMsg);
			} 
		);
	
	});
</script>


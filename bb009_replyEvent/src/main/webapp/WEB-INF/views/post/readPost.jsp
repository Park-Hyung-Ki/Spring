<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>
 
<!-- Begin Page Content -->
<div class="container-fluid">

</div>	

<!-- 댓글용도 modal 창 -->
<div id="modalReply" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        		<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>	<!-- End of modal-header -->
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label> 
					<input class="form-control" name='replyContent' value='New Reply!!!!'>
				</div>
				
				<div class="form-group">
					<label>Reply Date</label> 
					<input class="form-control" name='replyDate' value=''>
				</div>
				
			</div>	<!-- End of modal-body -->

			<div class="modal-footer"> <!-- End of modal-body -->
				<button id='btnModifyReply' type="button" class="btn btn-warning">Modify</button>
				<button id='btnRemoveReply' type="button" class="btn btn-danger">Remove</button>
				<button id='btnRegisterReply' type="button" class="btn btn-primary">Register</button>
				<button id='btnCloseModal' type="button" class="btn btn-default">Close</button>
			</div>	<!-- End of modal-footer -->
		</div>	<!-- End of modal-content -->
	</div>	<!-- End of modal-dialog -->
</div>
<!-- End of 댓글 입력 모달창 -->



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
		</div>
			<!--  
			<button data-oper='modify' class="btn btn-primary"
			 onclick="location.href='/post/modifyPost?boardId=${boardId}&postId=${post.id}'">수정</button> -->
			<!-- 이 data-oper는 modify라는 변수를 추가해서, html에서 제공하는 element의 변수명의 data를 추가할 수 있는 장치
			<button data-oper='list' class="btn btn-secondary"
			 onclick="location.href='/post/list?boardId=${boardId}'">목록으로</button> -->
			  
		<!-- html은 대소문자 구분안됨 -->
		<div class = "card-footer">
			<div class = "card-header">
			댓글
			<button id="btnOpenReplyModalForNew" class="btn btn-primary btn-xs pull-right">댓글 달기</button>
			</div>
			<div class = "card-body" >
				<ul id="ulReply">
				</ul>
			</div>
		</div>
		
	</div>
</div>
<!-- /.container-fluid -->

<!-- End of Main Content -->
<%@include file="../includes/footer.jsp"%>

<script type="text/javascript" src="\resources\js\post\reply.js"> </script>
<script type="text/javascript" src="\resources\js\util\dateFormat.js"> </script>

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
		
		
		var ulReply = $("#ulReply"); // 화면상에 존재하는 태그
		var originalId = "${post.id}";
		showReplyList(1); // Page 조회 처리, 1쪽 조회
		
		function showReplyList(pageNum) {
			replyService.getReplyList(
					{orgId:originalId, page:pageNum || 1},
					function(listReply) {
						if (listReply == null ||listReply.length == 0){
							//정보가 없을 시 ul에 담긴 내용 청소하고 처리 종료
							ulReply.html("");
							return;
						}
						// 댓글이 있으면 li로 만들어서 ul에 담을 것을 개발
						strLiTags = ""; // li Tag에 대한 문자열
						for(var i = 0, len = listReply.length || 0; i < len; i++){
							strLiTags += '<li class="clearfix" data-reply_id = "' + listReply[i].id +'">';
							strLiTags += '	<div>';
							strLiTags += '		<div>';
							strLiTags += '			<strong>' + listReply[i].writer.name + '</strong>';
							strLiTags += '			<small>' + dateFormatService.getWhenPosted(listReply[i].updateDate) + '</small>';
							strLiTags += '		</div>';
							strLiTags += '		<p>' + listReply[i].content + '</p>'; 
							strLiTags += '	</div>';
							strLiTags += '</li>';
						}
						ulReply.html(strLiTags);
					},
					
					function(errMsg) {
						alert("댓글 등록 오류:" + errMsg);
					} 
				);
			
		} 
		//replyService Module
		replyService.getReply(
				"000030000l",
				function(replyObj) {
					alert("댓글 상세  :" + replyObj);
				},
				function(errMsg) {
					alert("댓글 조회 오류:" + errMsg);
				}
			);
		
		var modalReply = $("#modalReply");
		var inputReplyContent = modalReply.find("input[name='replyContent']"); 
		var inputReplyDate = modalReply.find("input[name='replyDate']");
		
		var btnModifyReply = $("#btnModifyReply");
		var btnRemoveReply = $("#btnRemoveReply");
		var btnRegisterReply = $("#btnRegisterReply");
		
		//댓글 신규 용도의 modal창 열기
		$("#btnOpenReplyModalForNew").on("click", function(e) {
			//Modal에 들어 있는 모든 내용 청소하기
			modalReply.find("input").val("");
			// 신규 댓글 달을시에 등록일자는 Default 처리, 따라서 보여줄 필요가 없다.
			inputReplyDate.closest("div").hide();
			
			btnModifyReply.hide();
			btnRemoveReply.hide();
			btnRegisterReply.show();
			
			modalReply.modal("show");
		});
		
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


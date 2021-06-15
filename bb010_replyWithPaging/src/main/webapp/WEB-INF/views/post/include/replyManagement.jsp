<!-- 게시글 상세화면(readPost.jsp)에서 이 모든 것이 들어가 있으면 너무 복잡해집니다.
	 따라서 분할 정복으로 복잡도를 관리하자. 이는 유지보수성 향상으로 이어지는 것.  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 댓글 목록 출력 -->
<div class="card-footer">
	<div class="card-header">
		댓글
	<button id="btnOpenReplyModalForNew" class="btn btn-primary btn-xs pull-right">댓글달기</button>
	</div>
	<div class="card-body">
		<ul id="ulReply">
		</ul>
	</div>
<!-- /.container-fluid -->

<!-- 페이징처리 -->
<div id= "replyPaging" class='fa-pull-right'>
</div>
</div>
 
 <!-- 댓글입력 modal 창 -->
 <div id="modalReply" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        		<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<!-- End of modal-header -->
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label> 
					<input class="form-control" name='replyContent' value='New Reply'>
				</div>
				
				<div class="form-group">
					<label>작성자</label> 
					<input class="form-control" name='replyer' value=''>
				</div>
				
				<div class="form-group">
					<label>Reply Date</label> 
					<input class="form-control" name='replyDate' value=''>
				</div>
				
			</div>
			<!-- End of modal-body -->
			<div class="modal-footer">
				<button id='btnModifyReply' type="button" class="btn btn-warning">Modify</button>
				<button id='btnRemoveReply' type="button" class="btn btn-danger">Remove</button>
				<button id='btnRegisterReply' type="button" class="btn btn-primary">Register</button>
				<button id='btnCloseModal' type="button" class="btn btn-default">Close</button>
			</div>	<!-- End of modal-footer -->
		</div>	<!-- End of modal-content -->
	</div>	<!-- End of modal-dialog -->
</div>
<!-- End of 댓글 입력 모달창 -->
 
<script src="\resources\js\post\reply.js"></script>
<script src="\resources\js\util\dateFormat.js"></script>

<script type="text/javascript">
	var ulReply = $("#ulReply");
	var originalId = "${post.id}";
	var currentPageNum = 1;
	var replyPaging = $("#replyPaging");
	showReplyList(1);
	
	function showReplyList(pageNum) {
		replyService.getReplyList(
		{
			orgId : originalId,
			page : pageNum || 1
		}, 
		function(listReplyWithCount){
			var criteria = listReplyWithCount.first;
			var listReply = listReplyWithCount.second;
			
			if(listReply == null || listReply.length == 0){
				//정보가 없을 시 UL에 담긴 내용 청소
				ulReply.html("");
				return;
			}
			// 정보가 있으면 li로 만들어서 ul에 담을것 
			strLiTags = "";
			for (var i = 0, len = listReply.length || 0; i < len; i++){
				strLiTags += '<li class="clearfix" data-reply_id = "' + listReply[i].id + '">';
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
			
			displayPaging(criteria); 
			
		},function(errMsg){
			alert("댓글 등록 오류 발생" + errMsg);
			}
		);
	};
	//replyService 모듈
	//javascript에서 객체 만드는 방법 {속성이름 : 값}
	//originalId, reply, successCallBack, errorCallBack
	var modalReply = $("#modalReply")
	var inputReplyContent = modalReply.find("input[name='replyContent']");
	var inputReplyer = modalReply.find("input[name='replyer']");
	var inputReplyDate= modalReply.find("input[name='replyDate']");
	
	// LR"CUD" 순서
	var btnRegisterReply = $("#btnRegisterReply");
	var btnModifyReply = $("#btnModifyReply");
	var btnRemoveReply = $("#btnRemoveReply");
	
	//댓글 신규 용도의 모달 창 열기
	$("#btnOpenReplyModalForNew").on("click", function(e) {
		// 모달에 들어 있는 모든 내용 청소
		modalReply.find("input").val("");
		//신규 댓글 달기 시에는 등록일자는 Default 처리. 따라서 보여줄 필요가 없어요
		inputReplyDate.closest("div").hide();
		
		btnModifyReply.hide();
		btnRemoveReply.hide();
		btnRegisterReply.show();
		
		modalReply.modal("show");				
	});
	
	$("#btnCloseModal").on("click", function(e) {
		modalReply.find("input").val("");
		modalReply.modal("hide");
	});
	
	// 목록에서 특정 댓글을 클릭하면 해당 댓글의 상세 정보를 Ajax-rest로 읽고 이를 Modal 창에 보여준다
	// 특정 댓글은 동적으로 추가된 것이기에 이벤트 위임 방식을 활용하여야 한다. 
	
	ulReply.on("click", "li", function(e) { // "li" 이벤트 위임 방식
		var clickedLi = $(this);
		  
		replyService.getReply(
				clickedLi.data("reply_id"),  //clickedLi의 data 이름은 data-reply_id 이다.
				
			function(replyObj) {
				//수정 또는 삭제 시 댓글의 ID가 필요합니다.
				modalReply.data("reply_id", replyObj.id); // 댓글에 달린 Id의 객체가 없음 jsp에는, VO로부터 찾아서 가져와야함
				inputReplyContent.val(replyObj.content);
				inputReplyer.val(replyObj.writer.name);
				inputReplyDate.val(dateFormatService.getWhenPosted(replyObj.updateDate));
				
				inputReplyer.attr("readonly", "readonly");
				inputReplyDate.attr("readonly", "readonly");
				
				btnModifyReply.show();
				btnRemoveReply.show();
				btnRegisterReply.hide();
				
				modalReply.modal("show");
			}, 
			function(errMsg) {
				alert("댓글 상세 오류 : " + errMsg);
			}
		);
	});
	
	// 모달 창에서 내용을 사용자가 입력한 다음에 등록 버튼을 누르면 댓글로 등록하고 목록을 1쪽부터 다시 보여줌
	btnRegisterReply.on("click", function(e) {
		var reply = {
			content : inputReplyContent.val()
		};
		replyService.addReply(
			originalId, 
			reply, 
			function(newReplyId){
				modalReply.find("input").val(""); //모든 Input 요소를 찾고, 빈 문자열로 채워줄 것 = 값 청소 
				modalReply.modal("hide");
				//등록 성공시 다시 1쪽부터 보여준다.
				showReplyList(1);
			}, 
			function(errMsg){
				alert("댓글 등록 오류 발생" + errMsg);
			}
		);
	});
	
	/*댓글 상세 내용이 Modal 창에 나타났으며 작성자가 그 내용을 수정하고
	수정 버튼을 누르면 DB에 내용을 반영하고 목록으로 돌아온다.  */
	btnModifyReply.on("click", function(e) {
		replyService.updateReply(
			{
				id : modalReply.data("reply_id"),
			 	content : inputReplyContent.val()
			}, 
			
			function(resultMsg){
				modalReply.modal("hide");
				showReplyList(currentPageNum);
			}, 
			function(errMsg){
				alert("댓글 수정 오류 : " + errMsg);
			}
		);
	});
	
	/*댓글 상세 내용이 Modal 창에 나타났으며 작성자가 삭제 버튼을 누르면
	DB에 내용을 반영하고 목록으로 돌아온다.  */
	btnRemoveReply.on("click", function(e) {
		// replyId, successCallBack, errorCallBack
		replyService.removeReply(
			modalReply.data("reply_id"),
			
			function(delResult){
				modalReply.modal("hide");
				showReplyList(currentPageNum);
			}, 
			function(errMsg){
				alert("댓글 삭제 오류: " + errMsg);
			}
		);	
	});
	
	function displayPaging(cri) {
		strLiTags = "<ul id='ulPagination' class='pagination'>";
		if (cri.prev) {
			strLiTags += "<li class='page-item previous'>";
			strLiTags += "<a class='page-link' href='" + (cri.startPage - 1) + "'>&lt;&lt;</a>";
			strLiTags += "</li>";
		}
		
		for (var num = cri.startPage; num <= cri.endPage; num++) {
			strLiTags += "<li class='page-item " + (cri.pageNumber == num ? "active" : "" ) + "'>"; 
			strLiTags += "<a class='page-link' href=" + num + ">" + num + "</a>";
			strLiTags += "</li>";
		}
		
		if (cri.next) {
			strLiTags += "<li class='page-item next'>";
			strLiTags += "<a class='page-link' href='" + (cri.endPage + 1) + "'>&gt;&gt;</a>";
			strLiTags += "</li>";
		}
		strLiTags += "</ul>";
		replyPaging.html(strLiTags);
	}
	
	replyPaging.on("click", "li a", function(e) { // "li" 이벤트 위임 방식
		e.preventDefault();
	
		var clickedAnchor = $(this);
		currentPageNum = clickedAnchor.attr("href");
		showReplyList(currentPageNum);
 	});
	
</script>
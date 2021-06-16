<!-- 게시글 상세화면(readPost.jsp)에서 이 모든 것이 들어가 있으면 너무 복잡해집니다.
	 따라서 분할 정복으로 복잡도를 관리하자. 이는 유지보수성 향상으로 이어지는 것.  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 댓글 목록 출력 -->
<div class="card-footer">
	<div class="card-header">
		댓글
		<button id="btnOpenReplyModalForNew" class= "btn btn-primary btn-xs pull-right">댓글달기
		</button>
	</div>
	<div class="card-body">
		<!-- 원글에 달린 갯글 목록 Paging으로 출력하기 -->
		<ul id="ulReply">
		</ul>
	</div>

<!-- 페이징처리 -->
<div id= "replyPaging" class='fa-pull-right'>
</div>
</div>
 
 <!-- 댓글 입력 modal 창 -->
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
	var postId = "${post.id}";
	var currentPageNum = 1;
	var replyPaging = $("#replyPaging");
	showReplyList(1);
	
	function showReplyList(pageNum) {
		replyService.getReplyList(
		{
			orgId : postId,
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
					if (listReply[i].replyCnt > 0) {
						strLiTags += '		<i>[' + listReply[i].replyCnt + ']</i>';
					}
					strLiTags += '			<strong>' + listReply[i].writer.name + '</strong>';
					strLiTags += '			<small>' + dateFormatService.getWhenPosted(listReply[i].updateDate) + '</small>';
					strLiTags += ' 		<button class="btn btn-danger btn-xs pull-right"> 답글달기 </button>';
					strLiTags += '		</div>';
					strLiTags += '		<p>' + listReply[i].content + '</p>';
					strLiTags += '	</div>';
									/* 대댓글 전체 목록 삽입 장소
									그 구성 형식은 댓글 깊이에 따라서 다음과 같음
									<ul>
										<li> 하하하하
											<ul>
												<li> 호호호호호</li>
												<li> 호호호호호</li>
											</ul>
										</li>
									</ul>
									*/
					strLiTags += '</li>';
				}
				ulReply.html(strLiTags);
			
				replyPaging.html(criteria.pagingDiv);
			},function(errMsg){
				alert("댓글 등록 오류 발생" + errMsg);
			}
		);
	};
	
	//replyService 모듈
	//javascript에서 객체 만드는 방법 {속성이름 : 값}
	//postId, reply, successCallBack, errorCallBack
	var modalReply = $("#modalReply")
	var inputReplyContent = modalReply.find("input[name='replyContent']");
	var inputReplyer = modalReply.find("input[name='replyer']");
	var inputReplyDate= modalReply.find("input[name='replyDate']");
	
	// LR"CUD" 순서
	var btnRegisterReply = $("#btnRegisterReply");
	var btnModifyReply = $("#btnModifyReply");
	var btnRemoveReply = $("#btnRemoveReply");
	
	// 대댓글(답글)에서 답글 전체 조회 
	ulReply.on("click", "li i", function(e) { 
		e.preventDefault();
	
		var clickedLi = $(this).closest("li");
		var reply_id = clickedLi.data("reply_id");
		
		replyService.getReplyListOfReply(
				reply_id,
				
				function(listReply) {
					// 댓글이 있으면 li로 만들어서 ul에 담을 것
					strLiTags = clickedLi.html();
					strLiTags  += printReplyOfReplyByRecursion(listReply, 0, true);
					clickedLi.html(strLiTags);
					
				},function(errMsg){
					alert("댓글 등록 오류 발생" + errMsg);
				}
			);
		});
	/* 내것이 앞에 것 보다 깊으면 재귀호출하고 앞의 것의 닫기 tag를 완성
	<ul>
		<li>3 첫 대댓</li>
		<li>3 둘째 대댓
			<ul>
				<li> 4 첫 대댓 </li>
				<li> 4 첫 대댓 </li>
			</ul>
		</li>
		<li>3 세 대댓</li>
	</ul>
	*/
	
	//Call By Value	
	/* function printReplyOfReplyByRecursion(listReply, startIdx, startWithUl) {
		var strLiTags = "";
		if (startWithUl) {
			strLiTags = '<ul>';
		}
		var prev = null;
		for (var i = startIdx, len = listReply.length; i < len; i++) {
			if (prev == null) {
			strLiTags += '<li class="clearfix" data-reply_id = "' + listReply[i].id + '">';
			prev = listReply[i];
		
				} else if ( prev.depth == listReply[i].depth) {
					strLiTags += '</li>';
					strLiTags += '<li class="clearfix" data-reply_id = "' + listReply[i].id + '">';
					prev = listReply[i];
					
				} else if (prev.depth < listReply[i].depth) {
					strLiTags += printReplyOfReplyByRecursion(listReply, i, true); 
					prev = null;
					
				} else if (prev.depth > listReply[i].depth) {
					strLiTags += '</li>';
					strLiTags += '</ui>';
					return strLiTags;
				}
			}
			strLiTags += '</li>';
			strLiTags += '</ui>';
			return strLiTags;
		}; */
	

	//댓글 신규 용도의 모달 창 열기
	$("#btnOpenReplyModalForNew").on("click", function(e) {
		modalReply.data("original_id", postId);
		showModalForCreate();					
	});
	
	// 대댓글(답글) 신규 용도의 Modal 창 열기, 이부분도 이벤트 위임 방식으로 처리해야 할 것같음 이부분은 자손 결합자
	ulReply.on("click", "li button", function(e) { // "li" 이벤트 위임 방식
		e.preventDefault();
	
		var clickedLi = $(this).closest("li");
		modalReply.data("original_id", clickedLi.data("reply_id"));
		showModalForCreate();
	});
	
	function showModalForCreate() {
		// 모달에 들어 있는 모든 내용 청소
		modalReply.find("input").val("");
		//신규 댓글 달기 시에는 등록일자는 Default 처리. 따라서 보여줄 필요가 없어요
		inputReplyDate.closest("div").hide();
		
		btnModifyReply.hide();
		btnRemoveReply.hide();
		btnRegisterReply.show();
		
		modalReply.modal("show");	
	}
	
	//modal 창 닫기 
	$("#btnCloseModal").on("click", function(e) {
		modalReply.find("input").val("");
		modalReply.modal("hide");
	});
	
	// 목록에서 특정 댓글을 클릭하면 해당 댓글의 상세 정보를 Ajax-rest로 읽고 이를 Modal 창에 보여준다
	// 특정 댓글은 동적으로 추가된 것이기에 이벤트 위임 방식을 활용하여야 한다. 
	
	ulReply.on("click", "li p", function(e) { // "li" 이벤트 위임 방식
		var clickedLi = $(this).closest("li");
		  
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
			modalReply.data("original_id"),
			reply, 
			function(newReplyId){
				modalReply.find("input").val(""); //모든 Input 요소를 찾고, 빈 문자열로 채워줄 것 = 값 청소 
				modalReply.modal("hide");
				//등록 성공시 다시 1쪽부터 보여준다.
				showReplyList(1);
			},	function(errMsg){
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
	
	//Paging 중 하나를 선택하면 해당 쪽의 댓글 목록을 조회하여 갱신한다.
	
	replyPaging.on("click", "li a", function(e) { // "li" 이벤트 위임 방식
		e.preventDefault();
	
		var clickedAnchor = $(this);
		currentPageNum = clickedAnchor.attr("href");
		showReplyList(postId, currentPageNum);
 	});
	
</script>
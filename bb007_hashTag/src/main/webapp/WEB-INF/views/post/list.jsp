<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../includes/header.jsp"%>
<%@ page import="www.dream.com.bulletinBoard.model.PostVO"%>
<!-- Begin Page Content -->
<div class="container-fluid">


	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">${boardName}글 목록</h6>
		</div>
		<div class="card-body">
			<button id="btnregisterPost">글쓰기</button>
			<!-- 목록에서 버튼으로 가는 Btn -->

			<!--  <a href="/post/registerPost?boardId=${boardId}">글쓰기</a> -->
			<!-- 글쓰기 Button ↑ 05.25作 -->

			<!-- <input type="button" value="글쓰기" class="btn btn-primary"> ↑동일 표현 -->
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>수정일</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${listPost}" var="post">
							<tr>
								<td>
								<a class='anchor4post' href="${post.id}">${post.title}</a>
								<!-- 받아야할 변수가 여러개라서 class로 선언 -->
								</td>
								<td>${post.writer.name}</td>
								<td>${post.readCnt}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd"
										value="${post.updateDate}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- Paging 처리 05.27 -->
				<div class='fa-pull-right'>
					<ul id="ulPagination" class="pagination">
						<c:if test="${pagination.prev}">
							<li class="page-item previous"><a class='page-link'
								href="${pagination.startPage - 1}">&lt;&lt;</a></li>
						</c:if>
						<c:forEach var="num" begin="${pagination.startPage}"
							end="${pagination.endPage}">
							<li
								class='page-item ${pagination.pageNumber == num ? "active" : ""}'>
								<a class='page-link' href="${num}">${num}</a>
							</li>
						</c:forEach>
						<c:if test="${pagination.next}">
							<li class="page-item next"><a class='page-link'
								href="${pagination.endPage + 1}">&gt;&gt;</a></li>
						</c:if>
					</ul>
				</div>

				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">Modal title</h4>
							</div>
							<div class="modal-body">처리가 완료 되었습니다.</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save
									changes</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
				<!-- /.modal -->

			</div>
		</div>
	</div>

</div>
<!-- /.container-fluid -->

<!-- frmPaging 이벤트에서 Server로 요청보낼 인자들을 관리합니다. 05.27作 -->
<form id='frmPaging' action='/post/list/' method='get'>
	<input type="hidden" name='boardId' value='${boardId}'>
	<!-- Criteria.class와 짝지어진다.↑ value는 받은값으로 채워진다.-->
	<input type="hidden" name='pageNumber' value='${pagination.pageNumber}'>
	<input type="hidden" name='amount' value='${pagination.amount}'>
	<!-- amount에 대한 정보 ↑  .... 보낼때 필요한 정보名명과 받을때 필요한 정보명들과 Matching-->
</form>


<%@include file="../includes/footer.jsp"%>
<!-- End of Main Content -->
<script type="text/javascript">
	$(document).ready(function() {
	 // c에 들어있는 out tag를 활용, 저기 c는 제일 상단부에 taglib에 있음
	$("#btnregisterPost").on("click", function() {
		self.location = "/post/registerPost?boardId=${boardId}"; 
	});
		var result = '<c:out value="${result}"/>';
	
	// history.replaceState({}, null, null); 여기에 위치하면, 아무리 등록해도 Modal창이 안뜰것
	// 부르기 전에 상태를 넣었기때문에 글을 등록해도 Modal창이 안뜰것
	
	checkModal(result); // checkModal 함수 호출
	
	history.replaceState({}, null, null);
	// Modal창이 여러번 뜨는걸 방지하기 위한 Code1 (stateObj, title[, url]) -> Obj = {}이다 이는 null과 같은 의미
	// history객체야 상태를 변경하자, ({}, null, null) 한마디로 null이라는 상황이다. 넣어야하는 객체가 위처럼 3개라서 그렇지. 
	
	function checkModal(result){
		if (result === '' || history.state){ // Modal창이 여러번 뜨는걸 방지하기 위한 Code2 조건문을 추가.
			return;
		}
		if (result.length == ${PostVO.ID_LENGTH}) { //${PostVO.ID_LENGTH}
			// 5라는 literal을 쓰는건 굉장히 위험한 부분 왜냐하면, Id를 만드는 체계가 Partymapper.xml에서 보면
			// select get_id(seq_post_id.nextval) from dual 이러한 형식으로 개발이 되어있다.
			// 그리고예전에 만들었던 무작위 배열로 만든 5자리의 Id 개발 Class Oner는 바로 PostVO의 id와 체계가 맺혀져 있다.
			// PostVO에서 id부분을 상수로 바꿔준다.
			$(".modal-body").html("게시글 " + result + "번이 등록되었습니다.");
		} else {
			$(".modal-body").html("게시글에 대한 " + result + "하였습니다.");
		}
		
		$("#myModal").modal("show");
	}
	
	/*Paging 처리에서 특정 쪽 번호를 클릭하였을때 해당 page의 정보를 조회하여 목록을 재출력 해줍니다. */
	var frmPaging = $('#frmPaging');
	$('.page-item a').on('click', function(eInfo) {
		eInfo.preventDefault();
		$("input[name='pageNumber']").val($(this).attr('href')); //여기 val이 중요하다. Click이 일어난 곳=this 거기가 href 처리해둔곳
		frmPaging.submit();
	});
	
	/* 05.28 특정 게시물에 대한 상세 조회 처리 */
	$('.anchor4post').on('click', function(e) {
		e.preventDefault();
		var postId = $(this).attr('href')
		frmPaging.append("<input name='postId' type='hidden' value='" + postId + "'>"); // 문자열을 끝내고 이어받아서 return값 호출
		frmPaging.attr('action', '/post/readPost');
		frmPaging.attr('method', 'get');
		frmPaging.submit();
	});
	
});
	<!-- Paging 처리 05.27 여기서의 clas="값"과 PostController.class에서 getlist model부분 마지막값이 같아야함-->
</script>


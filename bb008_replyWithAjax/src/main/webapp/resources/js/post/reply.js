/**
 * 
 */
console.log("reply.js 연동 성공!!!");

/** 함수 정의 영역 */
var replyService = (function() {
	
	function add() {
		console.log("add 실행 됨!");
	}
	
	return {add:add};
})();
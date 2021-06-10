package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.dto.Criteria;

// 1. 홈페이지에서 Service를 제공하는 부분

// 3. 그리고 root-context에 scan 부분 추가해주고
// 4.  BoardVO에서 가져올 Board 목록을 보여주는getList 함수 작성
@Service // 2. 서비스 부분은 @Service 달기, 이건 Control또한 맟찬가지
public class ReplyService {
	@Autowired
	private ReplyMapper replyMapper;
	
	/* 댓글 목록 조회 */
	public List<ReplyVO> getReplyListWithPaging(String originalId , Criteria cri) {
		int idLength = originalId.length() + ReplyVO.ID_LENGTH;
		return replyMapper.getReplyListWithPaging(originalId, idLength, cri);
	}
	
	/* id로 찾기 */
	public ReplyVO findReplyById(String id) { // 조회
		return replyMapper.findReplyById(id);
	}
	/* Id값으로 Post객체 조회*/
	public int insertReply(String originalId , ReplyVO reply) {
		return replyMapper.insertReply(originalId, reply);
	}
	/* 댓글 수정 처리 */
	public int updateReply(ReplyVO reply) {
		return replyMapper.updateReply(reply);
	}

	/* 댓글 삭제 */
	public int deleteReplyById(String id) {
		return replyMapper.deleteReplyById(id);
	}

}

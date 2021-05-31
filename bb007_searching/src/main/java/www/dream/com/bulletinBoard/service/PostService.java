package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.dto.PostCriteria;
import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.PostMapper;
import www.dream.com.common.dto.Criteria;

// 1. 홈페이지에서 Service를 제공하는 부분

@Service // 2. 서비스 부분은 @Service 달기, 이건 Control또한 맟찬가지
// 3. 그리고 root-context에 scan 부분 추가해주고
// 4.  BoardVO에서 가져올 Board 목록을 보여주는getList 함수 작성
public class PostService {
	@Autowired
	private PostMapper postMapper;
	
	public long getTotalCount(int boardId) {
		return postMapper.getTotalCount(boardId);
	}
	
	public List<PostVO> getList(int boardId, Criteria cri){
		return postMapper.getList(boardId, cri);
	}
	
	//검색에 필요한 두 함수를 Service에 작성
	public List<PostVO> getListBySearchWithPaging(int boardId, PostCriteria cri) {
		return postMapper.getListBySearchWithPaging(boardId, cri);
	}
	
	public long getTotalCountBySearch(int boardId,PostCriteria cri) {
		return postMapper.getTotalCountBySearch(boardId, cri);
	}
	
	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return postMapper.findPostById(id);
	}

	public int insert(BoardVO board, PostVO post) {
		return postMapper.insert(board, post);
	}

	/** 게시글 수정 처리 */
	// boolean은 if처리를 하기때문에 변경해준것
	public boolean updatePost(PostVO post) {
		return postMapper.updatePost(post) == 1;
	}

	/** id 값으로 Post 객체 삭제 */
	public boolean deletePostById(String id) { // int -> boolean 변경 Redirect 하기위해서 
		return postMapper.deletePostById(id) == 1; // == 1 추가
	}
}

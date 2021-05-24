package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.BoardMapper;
import www.dream.com.bulletinBoard.persistence.PostMapper;

// 1. 홈페이지에서 Service를 제공하는 부분

@Service // 2. 서비스 부분은 @Service 달기, 이건 Control또한 맟찬가지
// 3. 그리고 root-context에 scan 부분 추가해주고
// 4.  BoardVO에서 가져올 Board 목록을 보여주는getList 함수 작성
public class PostService {
	@Autowired
	private PostMapper postMapper;
	
	public List<PostVO> getList(int boardId){
		return postMapper.getList(boardId);
	}
	
	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return postMapper.findPostById(id);
	}

	public int insert(BoardVO board, PostVO post) {
		return postMapper.insert(board, post);
	}

	/** 게시글 수정 처리 */
	public int updatePost(PostVO post) {
		return postMapper.updatePost(post);
	}

	/** id 값으로 Post 객체 삭제 */
	public int deletePostById(String id) {
		return postMapper.deletePostById(id);
	}
}

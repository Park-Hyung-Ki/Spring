package www.dream.com.bulletinBoard.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;

// PostVO -> PostMapper 작성
public interface PostMapper { // 추후 Data를 가져오기 위해서 Interface -> Mapper 생성
	//LRCUD
	/* Mapper에 들어가는 인자의 개수가 여러 개 일때는 필수적으로 @Param을 넣어줘야 합니다.
	 * 이를 실수하지 않기위하여 변수가 한 개여도 그냥 명시적으로 넣을 것 */
	public List<PostVO> getList(@Param("boardId") int boardId); // 1. 새로운 함수 하나 만들어주기, PostMapper.xml 에서 Data 전달을 표현하기위한
	
	public int insert(@Param("board") BoardVO board, @Param("post") PostVO post);
	// PostVO를 객체로 받는 insert 함수 객체 이름은 post @Param의 이름도 "post" 그리고 BoardVO에서 board id를 가져온다. 

	public PostVO findPostById(String id);
	
	/** 게시글 수정 처리 */
	public int updatePost(PostVO post);
	
	/** id 값으로 Post 객체 삭제*/
	public int deletePostById(String id);

}

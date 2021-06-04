package www.dream.com.bulletinBoard.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.PostMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.lengPosAnalyzer.PosAnalyzer;
import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.persistence.HashTagMapper;

// 1. 홈페이지에서 Service를 제공하는 부분

@Service // 2. 서비스 부분은 @Service 달기, 이건 Control또한 맟찬가지
// 3. 그리고 root-context에 scan 부분 추가해주고
// 4.  BoardVO에서 가져올 Board 목록을 보여주는getList 함수 작성
public class PostService {
	@Autowired
	private PostMapper postMapper;

	@Autowired
	private HashTagMapper hashTagMapper;

	public long getTotalCount(int boardId) {
		return postMapper.getTotalCount(boardId);
	}

	public List<PostVO> getList(int boardId, Criteria cri){
		return postMapper.getList(boardId, cri);
	}

	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return postMapper.findPostById(id);
	}

	public int insert(BoardVO board, PostVO post) {
		int affectedRows = postMapper.insert(board, post); // 게시글 자체를 등록
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post); // 06.01에 만든 PosAnalyzer FrameWork
		//수 많은 단어가 들어왔는데, 기존의 단어와 새롭게 들어올 단어를 분리해야할것 같음
		Set<String> setHashTag =  mapOccur.keySet(); // 단어 집합 자체를 등록
		CreateHashTagAndMapping(post, mapOccur, setHashTag);
		//최악을 고려해야 고품질의 코드를 만들어낼 수있다.
		return affectedRows; 
	}

	private void CreateHashTagAndMapping(PostVO post, Map<String, Integer> mapOccur, Set<String> setHashTag) {
		if (setHashTag.isEmpty()) {
			return;
		}

		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag); // 기존 단어집에서 찾아냅니다.
		for(HashtagVO hashtag : setExisting) {
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag())); //총 3번 감싸주었다.
		}


		for(HashtagVO hashtag : setExisting) {  
			setHashTag.remove(hashtag.getHashtag());
			// ↑에 들어있는게 신규 단어, 그것들을 위한 New Id가 필요한데, 어떻게 개발을 해야할까?
			// HashtagVO에 있는 hashtag 객체에서 저장되어있던 정보를 꺼내어, setHashTag에서 제거할 것(신규 단어)
		}
		
		Set<String> setNewHashTag = setHashTag; //제거하고 남은것들은 setNewHashTag 이름으로 선언 새롭게 출현할 단어들
		
		if (!setNewHashTag.isEmpty()) { //새로운 단어가 있으면 HashTag Table에 등록해줍니다. ↓ 

			int[] ids = StringUtil.convertCommnaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			int idx = 0;
			Set<HashtagVO> setHT = new HashSet<>(); //List 구조체
			
			for (String hashTag : setNewHashTag) {
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag); //HashTagVO에 관한 객체는 하나 만들어줬고
				newHashtag.setOccurCnt(mapOccur.get(hashTag));
				setHT.add(newHashtag);
			}
			hashTagMapper.createHashTag(setHT); // HashTag단어집에 이제, 신규 단어집이 들어간 것

			setExisting.addAll(setHT); 
		}
			
			hashTagMapper.insertMapBetweenPost(setExisting, post.getId()); //새로 들어간 단어집과, 기존의 단어집들간의
			
			/* hashTagMapper.insertMapBetweenPost(setHT, post.getId()) 64번째 줄
			 * hashTagMapper.insertMapBetweenPost(setExisting, post.getId()) 86번째 줄
			 * 구조가 동일하다. setHT, setExisting의 차이였는데, 저 둘의 자료형도
			 * Set<HashtagVO>로 동일하였다. 수정전 코드상에는 같은 함수를 두 번 출력하였기에
			 * setHT를 setExisting에 담아줌으로써, 해결
			 * 새 단어를 단어집에 넣었으니 기존 단어가 되어버린것  */
	}
	
	//06.04 검색기능을 추가한 화면을 만들기 위해서 새로운 기능 선언
	public List<PostVO> getListByHashTag(int boardId, Criteria cri){
		return postMapper.getListByHashTag(boardId, cri);
	}
	
	public long  getSearchTotalCount(int boardId, Criteria cri) {
		return postMapper.getSearchTotalCount(boardId, cri);
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

package www.dream.com.bulletinBoard.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.persistence.AttachFileVOMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.lengPosAnalyzer.PosAnalyzer;
import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.persistence.HashTagMapper;
import www.dream.com.party.model.Party;

// 1. 홈페이지에서 Service를 제공하는 부분

@Service // 2. 서비스 부분은 @Service 달기, 이건 Control또한 맟찬가지
// 3. 그리고 root-context에 scan 부분 추가해주고
// 4.  BoardVO에서 가져올 Board 목록을 보여주는getList 함수 작성
/**
 * 이는 ReplyVo와 PostVO의 Class 설계도를 기반으로 하는 것이며
 * 해당 Table을 Top 전략으로 통합하여 만들었기에 
 * ReplyMapper는 통합해 놓았다.
 * 그리고 PostService를 ReplyService와 분리하고
 */
public class PostService {
	@Autowired
	private ReplyMapper postMapper;

	@Autowired
	private HashTagMapper hashTagMapper;
	
	@Autowired
	private AttachFileVOMapper attachFileVOMapper; 

	// 이전 버전에 있던 getTotalCount getList 함수는 더이상 사용하지 않음 06.07
	
	public List<PostVO> getListByHashTag(Party curUser, int boardId, Criteria cri){
		if (cri.hasSearching()){
			String[] searchingHashtags = cri.getSearchingHashtags();
			if (curUser != null) {
				mngPersonalSearFavorite(curUser.getUserId(), searchingHashtags);
			}	
			return postMapper.getListByHashTag(boardId, cri);
		} else {
			return postMapper.getList(boardId, cri);
		}
	}
	
	public long  getSearchTotalCount(int boardId, Criteria cri) {
		
		if (cri.hasSearching()){
			return postMapper.getSearchTotalCount(boardId, cri);
		} else {
			//return postMapper.getTotalCount(boardId, PostVO.DESCRIM4POST);
			return postMapper.getTotalCount(boardId);
		}
	}

	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return (PostVO) postMapper.findReplyById(id);
	}
	
	@Transactional
	public int insert(BoardVO board, PostVO post) {
		int affectedRows = postMapper.insert(board, post); // 게시글 자체를 등록
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post); // 06.01에 만든 PosAnalyzer FrameWork
		//수 많은 단어가 들어왔는데, 기존의 단어와 새롭게 들어올 단어를 분리해야할것 같음
		Set<String> setHashTag =  mapOccur.keySet(); // 단어 집합 자체를 등록
		CreateHashTagAndMapping(post.getClass(), post.getId(), mapOccur, setHashTag);
		//최악을 고려해야 고품질의 코드를 만들어낼 수있다.
		
		// 첨부 파일 정보도 관리를 해야합니다. 高성능
		List<AttachFileVO> listAttach = post.getListAttach();
		if(listAttach != null && !listAttach.isEmpty()) {
			attachFileVOMapper.insert(post.getId(), listAttach);
		}
		return affectedRows; 
	}

	private void CreateHashTagAndMapping(Class targetType, String hashMapOpponentId, Map<String, Integer> mapOccur, Set<String> setHashTag) {
		if (setHashTag.isEmpty()) {
			//게시글에서 단어가 나타나지 않으면 처리할 것이 없음
			return;
		}

		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag); 
		// 기존 단어집에서 찾아내어 Match 해줍니다.
		for(HashtagVO hashtag : setExisting) {
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag())); //총 3번 감싸주었다.
		}

		for(HashtagVO hashtag : setExisting) {  
			setHashTag.remove(hashtag.getHashtag());
			// ↑에 들어있는게 신규 단어, 그것들을 위한 New Id가 필요한데, 어떻게 개발을 해야할까?
			// HashtagVO에 있는 hashtag 객체에서 저장되어있던 정보를 꺼내어, setHashTag에서 제거할 것(신규 단어)
		}
		
		Set<String> setNewHashTag = setHashTag; //제거하고 남은것들은 setNewHashTag 이름으로 선언 새롭게 출현할 단어들
		if (!setNewHashTag.isEmpty()) { 
			//새로운 단어가 있으면 HashTag Table에 등록해줍니다. ↓ 
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
		if (targetType == PostVO.class) {
				hashTagMapper.insertMapBetweenPost(setExisting, hashMapOpponentId); //새로 들어간 단어집과, 기존의 단어집들간의
			} else if (targetType == Party.class) {
				hashTagMapper.insertMapBetweenParty(setExisting, hashMapOpponentId); //새로 들어간 단어집과, 기존의 단어집들간의	
			
		}
			
			/* hashTagMapper.insertMapBetweenPost(setHT, post.getId()) 64번째 줄
			 * hashTagMapper.insertMapBetweenPost(setExisting, post.getId()) 86번째 줄
			 * 구조가 동일하다. setHT, setExisting의 차이였는데, 저 둘의 자료형도
			 * Set<HashtagVO>로 동일하였다. 수정전 코드상에는 같은 함수를 두 번 출력하였기에
			 * setHT를 setExisting에 담아줌으로써, 해결
			 * 새 단어를 단어집에 넣었으니 기존 단어가 되어버린것  */
	}
	
	//06.04 검색기능을 추가한 화면을 만들기 위해서 새로운 기능 선언
	
	/** 게시글 수정 처리 */
	// boolean은 if처리를 하기때문에 변경해준것
	@Transactional
	public boolean updatePost(PostVO post) {
		attachFileVOMapper.delete(post.getId());
		//첨부파일 정보고 관리 합니다.
		List<AttachFileVO> listAttach = post.getListAttach();
		if(listAttach != null && !listAttach.isEmpty()) {
			attachFileVOMapper.insert(post.getId(), listAttach);
		}
		return postMapper.updatePost(post) == 1;
	}

	/** id 값으로 Post 객체 삭제 */
	@Transactional
	public boolean deletePostById(String id) { // int -> boolean 변경 Redirect 하기위해서 
		attachFileVOMapper.delete(id);
		return postMapper.deleteReplyById(id) == 1; // == 1 추가
	}
	
	private void mngPersonalSearFavorite(String userId, String[] searchingHashtags) {
		//기존 단어와 신규 단어로 분할
		Set<String> setHashTag = Arrays.stream(searchingHashtags).collect(Collectors.toSet());
		Set<HashtagVO> setHT =  hashTagMapper.findExisting(setHashTag); 
		
		//기존 단어는 활용 횟수 올리기 
		//신규 단어는 단어 등록 및 활용 횟수는 1
		hashTagMapper.recordPersonalSearFavorite();
	}
}

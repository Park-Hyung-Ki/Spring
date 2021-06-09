package www.dream.com.restStudy.control;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.service.BoardService;
import www.dream.com.bulletinBoard.service.PostService;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Party;
import www.dream.com.party.model.User;
import www.dream.com.restStudy.model.DetailVO;
import www.dream.com.restStudy.model.MyVO;

// 일반 Controller에서 String으로 반환시 jsp를 반환함. RestController는 딱히 그런거 없음
// 06.09 Ajax 시작
@RestController
@RequestMapping("/sample/*")
public class SampleController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private BoardService boardService;
	
	
	/** 문자열 반환 연습 */
	@GetMapping(value = "getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		return "Hi";
	}

	/** 객체 반환 연습 */
	@GetMapping(value = "getObject", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public MyVO getObject() {
		MyVO ret = new MyVO();
		ret.setAsd(123);
		ret.setEfg("456");

		DetailVO a1 = new DetailVO();
		a1.setGgg("132");
		a1.setSss(1000);
		ret.add(a1);

		a1 = new DetailVO();
		a1.setSss(123);
		a1.setGgg("힘들다");
		ret.add(a1);

		return ret;
	}

	/** 객체 목록 반환 연습 */
	@GetMapping(value = "getList")
	public List<MyVO> getList() {
		List<MyVO> ret = IntStream.range(0, 10).mapToObj(i -> {
			MyVO newBie = new MyVO();
			newBie.setAsd(123);
			newBie.setEfg("안");

			DetailVO a1 = new DetailVO();
			a1.setGgg("1000");
			a1.setSss(333);
			newBie.add(a1);

			a1 = new DetailVO();
			a1.setGgg("안녕!");
			a1.setSss(419);
			newBie.add(a1);

			return newBie;
		}).collect(Collectors.toList());

		return ret;
	}

	/** ResponseEntity에 Map 반환연습 */
	@GetMapping(value = "getREMap")
	public ResponseEntity<Map<String, MyVO>> getREMap() {
		Map<String, MyVO> map = IntStream.range(0, 10).mapToObj(i -> {
			
			MyVO newBie = new MyVO();
			newBie.setAsd(i);
			newBie.setEfg("Park");

			DetailVO a1 = new DetailVO();
			a1.setGgg("Hyung");
			a1.setSss(333);
			newBie.add(a1);

			a1 = new DetailVO();
			a1.setGgg("Ki!");
			a1.setSss(419);
			newBie.add(a1);

			return newBie;
		}).collect(Collectors.toMap(MyVO::getKey, MyVO::getObj));
		return  ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	/** MAV 반환연습 */
	@GetMapping(value = "getJSP")
	public ModelAndView getJSP() {
		ModelAndView mav = new ModelAndView();
		
		MyVO newBie = new MyVO();
		newBie.setAsd(19);
		newBie.setEfg("Park");

		mav.getModel().put("data", newBie);
		
		mav.setViewName("sample/mav");
		return mav;
	}
	
	/** Redirect 반환연습 */
	@GetMapping(value = "getRedirect")
	public ModelAndView getRedirect() {
		ModelAndView mav = new ModelAndView();
		
		MyVO newBie = new MyVO();
		newBie.setAsd(19);
		newBie.setEfg("Park");
		
		mav.getModel().put("data", newBie);
		
		mav.setViewName("redirect:/sample/redirectedPage");
		return mav;
	}
	/** Redirect되는 요청에 대하여 반응하는 Handler */ 
	
	@GetMapping(value = "redirectedPage")
	public ModelAndView getredirectedPage() {
		ModelAndView mav = new ModelAndView();
		
		MyVO newBie = new MyVO();
		newBie.setAsd(19);
		newBie.setEfg("Park");
		
		mav.getModel().put("data", newBie);
		
		mav.setViewName("sample/redirectedPage");
		return mav;
	}
	
	/** PathVariable의 사용하여 요청 정보 처리 예 */
	// http://localhost:8091/sample/3/005sl 이런것이 Rest 방식
	@GetMapping(value = "{boardId}/{id}")
	public ModelAndView findPostById(@PathVariable("boardId") int boardId,
			@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView();
		
		mav.getModel().put("post", postService.findPostById(id));
		mav.getModel().put("boardId", boardId);
		mav.getModel().put("pagination", new Criteria());
		
		mav.setViewName("post/readPost");
		return mav;
	}
	
	/** PathVariable과 함께 requestParameter로 요청 처리 예 */
	// http://localhost:8091/sample/3?id=005so
	@GetMapping(value = "{boardId}")
	public ModelAndView findPostByReqId(@PathVariable("boardId") int boardId,
			@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();
		
		mav.getModel().put("post", postService.findPostById(id));
		mav.getModel().put("boardId", boardId);
		mav.getModel().put("pagination", new Criteria());
		
		mav.setViewName("post/readPost");
		return mav;
	}
	
	@PostMapping(value = "registerPost/{boardId}") // LCRUD 에서 Update 부분
	public PostVO registerPost(@PathVariable("boardId") int boardId,
		@RequestBody PostVO newPost) {
		BoardVO board = new BoardVO(boardId);
		Party writer = new User("hong");
		newPost.setWriter(writer);
		postService.insert(board, newPost);
		
		return postService.findPostById(newPost.getId());
	}
}

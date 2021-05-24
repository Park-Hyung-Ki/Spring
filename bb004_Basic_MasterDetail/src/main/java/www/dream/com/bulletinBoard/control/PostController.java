package www.dream.com.bulletinBoard.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import www.dream.com.bulletinBoard.service.PostService;

//Post에 관한 Controller Class를 만들어 낼것.
@Controller
@RequestMapping("/post/*")
public class PostController {
	@Autowired
	private PostService postService;
	
	/* 특정 게시판에 등록되어 있는 게시글을 목록으로 조회하기 P.212  void :/post/list.jsp로 변함*/
	@GetMapping(value="list")
	public void listPost(@RequestParam("boardId") int boardId, Model model) {
		// Model 객체를 사용해서 jsp로 정보를 넘김
		model.addAttribute("listPost", postService.getList(boardId)); // boardId를 집어넣으면 목록 정보가 나오겠지
	}
	
	@GetMapping(value="readPost")
	public void findPostById(@RequestParam("postId") String id, Model model) {
		model.addAttribute("post", postService.findPostById(id));
	}
}

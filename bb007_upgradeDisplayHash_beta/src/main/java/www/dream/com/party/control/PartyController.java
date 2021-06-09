package www.dream.com.party.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import www.dream.com.party.service.PartyService;

@Controller // 11. Controller @ 생성 , Controller @는 servelt.xml에 추가해줘야 한다.
@RequestMapping("/party/*") // @ 12. RequestMapping @ 생성
public class PartyController { // 8. PartyController class 생성
	@Autowired // 10. Autowired @ 생성
	private PartyService partyService; // 9. PartyClass와 이어줄거고
	
	@GetMapping(value="list") // 14. GetMapping @도 가져오고.
	public void getList(Model model) { // 13.getList 함수 생성 , // 15. Model 이거 구글링해보고 
		model.addAttribute("listParty", partyService.getList()); // 16. getlist를 불러오기위한 동작을 model에 담을것
		System.out.println("출력이 될까?");
	}
}

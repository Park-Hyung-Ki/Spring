package www.dream.com.party.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import www.dream.com.party.model.Party;

@Controller 
@RequestMapping(value = "/party/", method = {RequestMethod.GET, RequestMethod.POST}) 
// @sampleController에서 긁어온 파일 
public class PartyController {
	
	/*
	 * party/md를 Get 방식으로 부를 떄 작동하면서 인자와 객체 속성 이름을 짝지어서 객체까지 만들어 줍니다.
	 
	@GetMapping(value = "md") // 8. Party를 활용하기 위한 함수
	public void createParty(Party obj) { // 9. Party를 활용하기 위해서 불러오고
		System.out.println(obj); //10. obj를 출력 
	}*/
	
	/*
	 * party/registerParty를 호출하면 회원가입 홈페이지를 엽니다.
	 */
	@GetMapping(value="registerParty") // 11. View/party/registerParty.jsp를 열기위한 함수 생성
	public void openRegisterPartyView() { // 12. 함수이름 여기는 따로 인자 받아줄거 없음
		System.out.println("회원가입 홈페이지를 출력합니다.");
		// 13. http://localhost:8090/com/party/registerParty
		// ↑ server debug하고 주소입력하면 jsp 출력
	}
	
	@PostMapping(value="createParty")
	public void createPartyVer2(Party obj) {
		System.out.println(obj);
	}
}

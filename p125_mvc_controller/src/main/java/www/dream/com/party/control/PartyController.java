package www.dream.com.party.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import www.dream.com.party.model.ContactPoint;
import www.dream.com.party.model.ContactPointType;
import www.dream.com.party.model.Party;

@Controller 
@RequestMapping(value = "/party/*", method = {RequestMethod.GET, RequestMethod.POST}) 
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
		// 13. http://localhost:8090/party/registerParty
		// ↑ server debug하고 주소입력하면 jsp 출력
	}
	/*
	@PostMapping(value="createParty") // 14. Post 방식의 함수
	//public void createParty(Party obj, Model model) { //18. Model 이라는 Date를 담아줄 것
	public String createParty(Party obj, Model model) { // 21. String이라는 새로운 방식으로
		System.out.println(obj);
		obj.setId(55); // 20. obj에 55라는 값을 넣어줬다고 하ㅏ자.
		model.addAttribute("newbie", obj); // 19. 새로운 코드 생성 newbie라는 객체에 model의 데이터를 담아줄 것
		// newbie: 변수명 , obj : 객체의 느낌
		// Java Source의 느낌 ↓
		// Party newbie = obj;
		// return newbie;
		return "party/confirmParty"; // 22. 21과 이어지기에 새로운 return 값을 만들어주자.
	}
	*/
	
	/**
	 * ModelAndView는 AJax에서 주로 사용합니다. 
	 */
	
	// 25. ModelAndView  Ajax와 연동한 부분 확인하기 // 이거 사용하려면 윗부분 주석처리 해야함.
	@PostMapping(value="createPartyAjax")  // jsp 부분도 createPartyAjax로 바꿔주고
	public ModelAndView  createPartyAjax(Party obj) { // Model model은 Return 구조안에 있으니 제거
	ModelAndView ret = new ModelAndView("party/confirmParty"); // ret 값으로 받을거기에 정의해주고
	//party/confirmParty 이렇게쓴 이유는 이렇게 해놓지 않으면 debug on server했을때 검색을 못하더라
	System.out.println(obj);
	obj.setId(55); 
	ret.addObject("newbie", obj); // 전체적인 변경 부분은 바로 위의 함수와 비교해보는걸로 
	return ret; // 순서는 크게 상관없으니 이런식으로 바꿔주고
	// Result : Party(id=0, name=PPARk, birthDate=Wed Apr 28 00:00:00 KST 2021, sex=false, job=null, listContactPoint=[ContactPoint(contactPointType=addr, value=432-21), ContactPoint(contactPointType=phoneNum, value=null)])
	// 
	}
	
	// 26. 새로운 url을 만들어 봅시다.
	@PostMapping(value="createPartyByDefalut") // createPartByDefalut 여기 바꾸면 jsp에서 상단 value도 바꿔줘야 함  
	public String createParty(Party obj, @ModelAttribute("ageOfMine") int ageOfMine) { // 28. 새로운 값을 하나 보여주기 위하여 생성 
	System.out.println(obj);
	System.out.println("내사람의 나이는 " + ageOfMine);
	obj.setId(56); 
	return "party/confirmParty";
	//Party(id=0, name=Park, birthDate=Thu May 06 00:00:00 KST 2021, sex=true, job=null, listContactPoint=[ContactPoint(contactPointType=addr, value=11234), ContactPoint(contactPointType=phoneNum, value=null)])
	//내사람의 나이는 24 어찌저찌 나오긴합니다.
	}
	
	@PostMapping(value="createPartyRedirect") // jsp에서 상단 value도 바꿔줘야 함  
	public String createPartyRedirect(Party obj, Model model, RedirectAttributes rttr) { // 30. 새로운 값을 하나 보여주기 위하여 생성 
	System.out.println(obj);
	obj.setId(56);
	rttr.addAttribute("newbie", 419); // addFlashAttribute는 일단 사용하지 않는걸로, 안하니까 잘만나온다. 
	return "redirect:/party/redirectedParty";
	}
	
	@GetMapping(value="redirectedParty") // jsp에서 상단 value도 바꿔줘야 함  
	public String confirmPartyRedirect(@RequestParam("newbie") int newbie, Model model) { // 31. Request 받기위해서 30과 이어지는 값을 하나 새로 만들어야함 
	System.out.println(newbie);
	Party obj = new Party(); // 37. @RequestParam("newbie")의 부분때문에 새롭게 추가.
	obj.setId(57); // 33. 그냥 이 부분은 값이 순차적으로 잘 들어가고있는지 위의 값들과 차별을 주는것.
	model.addAttribute("newbie", obj); // 32. 이 값이 있어야 화면에 출력이 될테니
	return "/party/confirmParty"; // confirmParty에 정보를 주는 방법
	// 35. redirected 부분은 Post를 사용하는가 Get 방법을 사용하는가? -> Get 방식을 사용하여 재요청
	// 36. id 부분은 출력이 잘되나, name부분은 값이 넘어오지 않는다.
	// *redirect할때 객체를 막 던지지 않는다.
	}
	
	@GetMapping(value = "getJson") // 40. JSON 사용을 위한 함수 , 얘는 따로 안바꿔줘도 상관이없다.
	public @ResponseBody Party /* ← Return Type 지정 */ getJsonOfParty() {
		//Request 인자 없이 객체 그냥 넣어서 Return 형식으로
		Party obj = new Party();
		obj.setName("형기");
		obj.setId(58);
		// url 입력 : http://localhost:8090/party/getJson
		// Result : {"id":58,"name":"형기","birthDate":null,"sex":false,"job":null,"listContactPoint":[]}
		
		ContactPoint cp = new ContactPoint(); // 42. ContactPoint를 확인하기 위한 변수 선언
		cp.setContactPointType(ContactPointType.phoneNum);
		cp.setValue("010-1234-5678");
		obj.addCP(cp); // cp에 담기 위함
		
		cp = new ContactPoint(); // 43. 이부분도 마찬가지. 변수 선언
		cp.setContactPointType(ContactPointType.addr);
		cp.setValue("서울");
		obj.addCP(cp);
		// Result : {"id":58,"name":"형기","birthDate":null,"sex":false,"job":null,"listContactPoint":[{"contactPointType":"phoneNum","value":"010-1234-5678"},{"contactPointType":"addr","value":"서울"}]}
		
		return obj;
		//JSon 형식의 Return은 AJax, 중요한거고 많이 쓴다. 이유는 "성능"
		// Server Side에서는 RestAPI 라고 하는데, 일단 기억해두고 있다.
		// ResponseEntity 라는 것은 Ajax 하고궁합 맞춰서 많이 사용한다.
	}
}

package www.dream.com;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import www.dream.com.sample.model.SVO4Debugging;
import www.dream.com.sample.model.SampleMasterVO;
import www.dream.com.sample.model.SampleVO;

@Controller // 1. Servlet Component로 Container에 담기기 위해서는 우리가 어디를 더 보고 확인해야 하나요?
			// 내가 다루려는게 Scan의 대상인지 servelt-context.xml에 경로를 설정해줘야 한다.(맨 밑에부분)
@RequestMapping("/sample/") // 2. 이 친구의 역할은 현재 클래스의 모든 메서드들의 기본적인 URL 경로가 됩니다.
// 이 함수는 sample까지 기본 장착
public class SampleController {
	/**
	 * sample/ 부르면 작동 근데 정확히는 (/sample/) 입니다. 앞에다가붙이니 주석이 날라감
	 */
	@RequestMapping("") // 3.이렇게 아무것도 없는것도 만들수 있는데
	public void basic() { // 4. basic 함수를 만들어주고
		System.out.println("basic()을 실행합니다."); // 5. 일단 테스트하기위해서 만든 함수의 내용
		// 6. http://localhost:8090/com/sample/이러한 주소를 넣어줘야 console 창에서 결과(5의 내용)가 출력됨.
	}

	/**
	 * sample/gp/를 GET 또는 POST 방식으로 작동
	 */
	@RequestMapping(value = "gp", method = { RequestMethod.GET, RequestMethod.POST })
	// 8. 속성을 변경해야한다. RequestMapping 이라는 @ 기능을 통해서 우리는 GET 방법 POST방법 둘다 사용할겁니다.
	public void basic4getAndPost() { // 7. 새로운 함수 만들기
		System.out.println("이 basic() 함수는 GET,POST 방법 둘다 가능합니다.");
		// http://localhost:8090/com/sample/gp 여기까지 입력
	}

	/**
	 * sample/get/를 GET 방식으로 작동
	 */
	@GetMapping(value = "get") // 10. value 값을 바꿔줘야 하고
	public void basic4getOnly() { // 9. 새로운 함수 만들기
		System.out.println("이 basic() 함수는 GET 방법만 가능합니다.");
		// http://localhost:8090/com/sample/get 이라고 하면
		// 이 basic() 함수는 GET,POST 방법 둘다 가능합니다.(얘는 gp가 아니더라도 get, post를 둘다 지원하니까 출력이 됨)
		// 이 basic() 함수는 GET 방법만 가능합니다. 이렇게 둘다 결과가 출력이 된다.
	}

	/*
	 * sample/post/를 POST 방식으로 작동
	 */

	@PostMapping(value = "post") // 12. value 값을 바꿔줘야 하고
	public void basic4postOnly() { // 11. 새로운 함수 만들기
		System.out.println("이 basic() 함수는 Post 방법만 가능합니다.");
		// Post 방식은 뭔가 따로 설정이 필요하다고 그래서 따로 해보진 못했다.
	}

	/*
	 * sample/vo/를 Get방식으로 작동하면서 인자와 객체 속성 이름을 짝지어서 객체까지 만들어 줍니다.
	 * 
	 */

	@GetMapping(value = "vo") // SampleVO.java 를 활용하기 위해서 만든 변수
	public void basic4SampleVO(SampleVO obj) { // SmapleVO를 사용할거니까
		System.out.println("Sample.vo를 활용하기 위한 함수 입니다.");
		System.out.println(obj);
	}
	// http://localhost:8090/com/sample/vo?name=형기age=26 이런식으로
	// 저기 이름이 깨져서 저렇게 나오는데 내이름 넣었습니다.
	// Result
	// Sample.vo를 활용하기 위한 함수 입니다.
	// SampleVO [name=형기, age=26]

	/*
	 * sample/param/을 GET 방식으로 부를때 작동하면서 인자(name , ag)까지 변수로 자동 형변환 해줍니다.
	 */
	@GetMapping(value = "param") // param 이라는 @요소를 사용하여 확인
	public void basic4Param(@RequestParam("name") String name, @RequestParam("ag") int age) { // 함수의 내용및 형식부분
		System.out.println("name = " + name);
		System.out.println("age = " + age);
		// Overloading 되어서 함수 이름이 굳이 같아도 상관은없긴하다.
		// http://localhost:8090/com/sample/param?name=형기&ag=26
		// Reulst
		// name = 형기
		// age = 26

	}

	@GetMapping(value = "svo4")
	public void basic4SVO4Debugging(SVO4Debugging obj) {
		System.out.println(obj);
		// http://localhost:8090/com/sample/svo4?name=형기&age=26
		// result
		// SVO4Debugging [name=형기, age=26]
	}

	/*
	 * List<String> interfapce로는 객체 생성을못해서 오류가 난다.
	 * 
	 * @RequestParam("ids")로 지정해주지 않았더니 List객체로 Mapping이 안됨
	 * 
	 */
	@GetMapping(value = "list") // 교재 133쪽의 내용을 test 하기 위해서 만드는 부분
	public void basic4List(@RequestParam("ids") ArrayList<String> ids) { // url을 list화 해서
		for (String str : ids) { //
			System.out.print(str);
			// List는 못사용한다. 오류가 나온다 tomcat server에서
			// 그래서 List -> ArrayList로 변경
			// http://localhost:8090/com/sample/list?ids=hyungki&ids=26&ids=male&ids=179
			// result : hyungki26male179
		}
	}

	/*
	 * 배열의 경우 동일 이름이 @RequestParam이 없어도 구동이 되기에 ArrayList 보다 더 편리하다.
	 * 
	 */
	@GetMapping(value="arr") // 교재 134쪽의 내용을 test 하기 위해서 만드는 부분
		public void basic4Array(String[] ids) { // url을 list화 해서
			//public void basic4Array(@RequestParam("ids")String[] ids) { 로 해도 문제는 없다.
			for (String str : ids ) { // 
				System.out.print( str );
				//http://localhost:8090/com/sample/arr?ids=hyungki&ids=26&ids=Seoul
				// result : hyungki26Seoul
		}
	}
	/*
	 * sample/masterDetail을 Get방식으로 처리하는 함수
	 * ~/sample/masterDetail?id=0001&listSampleVO%5B0%5D.name=형기&listSampleVO%5B1%5D.name=hyungki&listSampleVO%5B0%5D.age=26&listSampleVO%5B1%5D.age=27
	 * [] tomcat에 따라서 지원 안할수도 이음 ☆ [:%5B  ]:%5D☆ 로 바꿔줘야 한다.
	 */
	@GetMapping(value = "masterDetail") // SampleMasterVO를 활용하기 위한 함수
	public void basic4MasterDetail(SampleMasterVO obj) { // SampleMasterVO를 활용하기 위해서 불러오고
		System.out.println(obj); // obj를 출력 [name , age , id]
		
		//SampleMasterVO [id=1, listSampleVO=SampleVO [name=형기, age=26]SampleVO [name=hyungki, age=27]]
		// 근데 ↑ 출력해주려면 @Data라는 강력한 @를 사용해야함 일반적인 방법으로는 계속 출력 못했음
	}
}

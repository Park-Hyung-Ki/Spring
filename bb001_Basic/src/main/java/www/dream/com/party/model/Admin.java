package www.dream.com.party.model;

import lombok.Data;

/**
 * 사장님(운영자 등록, 삭제 권한 있음)
 * @author Park
 */
@Data
 // 7. Party를 상속받는 Admin 클래스 생성 // 21. 잠시 상속구조 끊어내고 Admin에서 Test 하면 잘 나온다.
public class Admin extends Party {
	public String toString() {
		return "Admin [toString()=" + super.toString() + "]";
		// Data Test를 하기위한 toString 구문.
	} 
	
}

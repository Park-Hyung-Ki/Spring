package www.dream.com.party.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원
 * @author Park
 */
@Data
@NoArgsConstructor
public class User extends Party {public User(String userId) {
		super(userId);
	} // 7-2. Party를 상속받는 User 클래스 생성
	
	

}

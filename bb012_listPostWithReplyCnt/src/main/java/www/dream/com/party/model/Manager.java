package www.dream.com.party.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 시스템 운영자
 * @author Park
 */
@Data
@NoArgsConstructor
public class Manager extends Party {public Manager(String userId) {
		super(userId);
	} // 7-1. Party를 상속받는 Manager 클래스 생성
	
	

}

package www.dream.com.party.model;

import lombok.Data;

@Data // 3. @Data 생성 이곳도 1:N 구조 , @Data라서 ToString 기능이 포함되어 있다. 
public class ContactPoint {
	// 일단 이러한 정보들이 이습니다.
	// private long id; DB에는 있어야하는데 여기에는 필요없음
	private ContactPointType contactPointType; // 2
	private String value;

}

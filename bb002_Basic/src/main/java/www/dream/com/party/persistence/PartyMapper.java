package www.dream.com.party.persistence;

import java.util.List;

import www.dream.com.party.model.Party;

/**
 * Mybatis를 활용하여 Party 종류의 객체를 관리하는 인터페이스
 * @author Park
 *
 */
public interface PartyMapper { // 13. persistence package에 PartyMapper interface 작성
	// 함수 정의 순서 LRCUD
	// 목록 조회
	//@Select("select * from s_party") // 15. @Select를 사용하여, sql에 만들어놓은 s_party Table을 불러내볼것
	public List<Party> getList(); //14. List Type으로 Return 받는 목록조회 함수 getList 생성 , Table에 있는 User 정보를 읽어보자.
	// public List<Admin> getList(); // 22. Admin에다가 변수 넣어서 Test 해보는 것, 
	// 개별 객체 조회
	// Insert
	// Update
	// Delete
}

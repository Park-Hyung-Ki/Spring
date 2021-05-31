package www.dream.com.party.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import www.dream.com.party.model.Party;
import www.dream.com.party.persistence.PartyMapper;

@Service // 2. @Seivce @ 생성 그리고 root-context.xml 로 가서 tag 작성 service는 root-context에 생성
@AllArgsConstructor // 4. Allargsconstructor @ 생성
public class PartyService { // 1. 순서가 의미가 없어졌다. 일단은 두 가지 CommonMngVO , PartyMapper의 xml을 만들었고, 시작한다 이부분은.
	@NonNull // 5. NonNull @도 만들어준다. 일단 이친구의 기능이뭔지 공부하기.
	private PartyMapper partyMapper; // 3. PartyMapper 객체를 받아낼 것

	public List<Party> getList(){ // 6. Party List를 끌고와도 괜찮다. 왜? List를 불러오는 함수를 만들거라서
		return partyMapper.getList(); // 7. public으로 만들었으니, 그에대한 반환값必 partyMapper 변수의 getList 함수 선언 
	}
}

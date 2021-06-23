package www.dream.com.party.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.party.model.ContactPoint;
import www.dream.com.party.model.User;

// 17. Test를 하기위한 @의 모임. 
@RunWith(SpringJUnit4ClassRunner.class) // 5. test 하기위해서 아까했던 TestDI를 가져오고
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PartyMapperTest { // 16. Junit test case를 만들고, root-context.xml에 mybatis scan 경로 설정해주고
	
	@Autowired // 18. 
	private PartyMapper partyMapper;  // 17. private형 변수 선언

	@Test
	public void test() {
		assertNotNull(partyMapper); // 19. assertNotNull 생성
		try {
			partyMapper.getList().forEach(p ->{System.out.println(p);}); // 18.GetList를 Test하기위한 구문
		} catch(Exception e) {
			e.printStackTrace(); // 20. try-catch문으로 오류 확인
		}
	}
}

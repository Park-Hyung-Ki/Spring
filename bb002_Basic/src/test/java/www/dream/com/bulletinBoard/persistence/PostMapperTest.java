package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.party.model.Admin;

// 17. Test를 하기위한 @의 모임. 
@RunWith(SpringJUnit4ClassRunner.class) // 5. test 하기위해서 아까했던 TestDI를 가져오고
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//0521 Board의 속성 Data 값을 확인하기 위한 Junit Test

/**
 * JUnit Test를 할때, 함수의 선언 결과가 오름차순 순으로 정렬이 되어 출력이 된다.
 * 그래서 밑에 testgetB , testgetL B가 더 높기때문에 B쪽 함수가 먼저 출력됨.
 * @author Park
 */
public class PostMapperTest { 
	
	@Autowired // . 
	private PostMapper postMapper;  // 
	
	@Test
	public void testinsert() { // insert() 함수를 만들어 낼것
		try {
			BoardVO commNotice = new BoardVO(1);
			PostVO post = new PostVO("아름다운 이 강산", "인생이 쉽지가 않네요", new Admin("admin")); //PostVO는 Party가 먼저 필요하고,
			postMapper.insert(commNotice, post);
			System.out.println("지금 만든 객체의 Id는 " + post.getId());
			//↑책 190쪽에 있는것 처럼 개발을 하면, id의 값이 안나온다.
			
		} catch(Exception e) {
			e.printStackTrace(); // 
		}
	}
	

	@Test
	public void testgetList() {
		assertNotNull(postMapper); // 
		try {
			postMapper.getList(1).forEach(p ->{System.out.println(p);}); // 
		} catch(Exception e) {
			e.printStackTrace(); // 
		}
	}
}
	

package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.dto.PostCriteria;

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
public class SearchMapperTest { 
	
	@Autowired
	private PostMapper postMapper;
	
	
	/*@Test
	public void test040GetList() {
		assertNotNull(postMapper);
		Map<String, String> map = new HashMap<>();
		map.put("T", "Monody");
		try {
			postMapper.getListBySearch(3, map).forEach(post->
			{System.out.println(post);
			});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	@Test
	public void test042GetList() {
		assertNotNull(postMapper);
		PostCriteria cri = new PostCriteria();
		cri.setAmount(10);
		cri.setPageNumber(1);
		cri.setType("TC");
		cri.setKeyword("Monody"); // 생성자가 없어서 set함수를 이용해서이렇게 만들어줌
		try {
			postMapper.getListBySearchWithPaging(3, cri).forEach(post->{
				System.out.println(post);
			});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test044GetList() {
		assertNotNull(postMapper);
		PostCriteria cri = new PostCriteria();
		cri.setAmount(10);
		cri.setPageNumber(1);
		try {
			postMapper.getListBySearchWithPaging(3, cri).forEach(post->{
				System.out.println("=======구분짓기======");
				System.out.println(post);
			});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	

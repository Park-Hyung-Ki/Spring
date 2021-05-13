package www.dream.com;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) // 5. test 하기위해서 아까했던 TestDI를 가져오고
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 //3. MapperTest 만들어보고 
public class TimeMapperTest {
	
	@Autowired //4. Autowired 시켜보고
	private TimeMapper timeMapper; // 4. 변수 선언


	@Test
	public void test() { // 6. 함수 만들기
		assertNotNull(timeMapper);
		System.out.println(timeMapper.getTime());
	}
	
	@Test
	public void testByxml() { // 5. XML과 연동하는 함수 만들기
		assertNotNull(timeMapper);
		System.out.println(timeMapper.getTimeByxml());
	}

}

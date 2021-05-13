package www.dream.com.di_sample;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDI {
	@Autowired
	private Restaurant restaurant;
	
	@Autowired
	private Hotel hotel;
	

	@Test
	public void test() {
		restaurant.getChef().setName("홍길동");
		//객체가 들어가있음을 보장
		assertNotNull(restaurant);
		System.out.println(restaurant.getChef());
	}
	
	@Test
	public void testHotel() {
		assertNotNull(hotel);
		System.out.println(hotel.getChef());
	}

}



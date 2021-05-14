package www.dream.com.sample.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VO : Value Object : 값을 담고 있는 객체 화면에서부터 table까지 활용되는 것.
 * 
 * DTO : Data Transfer Object
 *
 */
 @NoArgsConstructor// 2. @NoArgsConstructor 추가, POJO 규약이다. 변수 선언할거면 무조건 있어야한다.
public class SampleVO {
	@Setter // 3. Setter를 사용 할것
	private String name; // 1. 가장 무난하게 테스트할때 쓰이는 변수들 이름, 나이
	@Setter
	private int age;
	
	@Override // source - tostirng 만들어줬는데, 얘는 lombok으로 쓰지말고 최대한 만들어주는걸로 하는게 낫다.
	public String toString() {
		return "SampleVO [name=" + name + ", age=" + age + "]";
	}
	
}

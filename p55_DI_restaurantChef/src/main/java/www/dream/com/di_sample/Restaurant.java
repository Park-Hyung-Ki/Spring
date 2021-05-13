package www.dream.com.di_sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Repository //  4: 선언 Data Class로 사용할 POJO라고 할수있지
@NoArgsConstructor //  4-1 : 선언
public class Restaurant { // 3 Res Class 만들고
//5: DI를 위해서 
	@Autowired // 7.한번 했었는데 일단 선언
	@Getter // 7-1 얘도 선언
	private Chef chef; //  6. 주방에서 일하는 Chef 생성
}

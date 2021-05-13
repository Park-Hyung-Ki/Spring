package www.dream.com.di_sample;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Repository // Data 라는 뜻 // 1
@NoArgsConstructor // 생성자, 얘는 프로젝트 시작할때 무조건 넣어줘야함 // 2
@Scope("prototype")

public class Chef {
	@Setter
	@Getter
	private String name = "";
	@Override
	public String toString() {
		return "멋진" + name +"주방장님";
	}
	

}

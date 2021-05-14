package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class Party {
	// Data 재구성을 하려고하는데 이 부분은 고객의 기본 정보들
	private long id;
	private String name;
	private Date birthDate;
	private boolean sex;
	private String job;
	
	private List<ContactPoint> listContactPoint = new ArrayList<>(); //Staruml에서 연관 관계를 표현
	
}
